# Guideline Component Sidebar (Menu trái thống nhất)

## 1. Mục tiêu
Chuẩn hoá sidebar để khi thay đổi (màu sắc, font, thêm mục) chỉ sửa một nơi trong code frontend.

## 2. Cấu trúc đề xuất (Angular / React tư duy tương tự)
```
components/
  layout/
    sidebar/
      sidebar.component.ts(x)
      sidebar.component.html
      sidebar.component.scss
      sidebar.config.ts (danh sách các item)
      sidebar.types.ts
```

## 3. Model dữ liệu SidebarItem
```ts
export interface SidebarItem {
  id: string;              // unique
  label: string;           // "Dự án", "Hợp đồng"...
  icon: string;            // tên icon (theo sprite hoặc Material icon key)
  route?: string;          // router link
  children?: SidebarItem[];// menu con (nếu cần mở rộng)
  badge?: {
    type: 'info' | 'warn' | 'danger';
    value: string | number;
    title?: string;        // tooltip
  };
  featureFlag?: string;    // ẩn/hiện theo flag
  permissions?: string[];  // RBAC
  exact?: boolean;         // match route chính xác
}
```

## 4. Danh sách mặc định (2025-09)
| id | label | icon | route |
|----|-------|------|-------|
| project | Dự án | project | /projects |
| contract | Hợp đồng | contract | /contracts |
| vendor | Nhà thầu | vendor | /vendors |
| knowledge | Kho tri thức | knowledge | /knowledge-base |

Có thể bổ sung: báo cáo, cài đặt, thông báo... sau.

## 5. Quy ước icon (16x16 hoặc 20x20)
- project: khối 2 tầng (như trong SVG: rectangle lớn + thanh nhỏ phía trên)
- contract: tờ giấy có 3 dòng kẻ
- vendor: 2 hình tròn (đại diện avatar) + đường cong đáy (group)
- knowledge: cuốn sách mở (hai trang, đường chia giữa)
- Màu active: #FFFFFF trên nền item active (#1565C0 hoặc #1976D2 đậm hơn nền chính)

## 6. States
| State | Màu nền | Màu chữ/icon | Border |
|-------|---------|--------------|--------|
| Normal | #1976D2 | #FFF | none |
| Hover | #1565C0 | #FFF | none |
| Active | #1565C0 (đậm hơn hoặc gradient nhẹ) | #FFF | left border 3px #FFF (optional) |
| Disabled | #1E609C (giảm sáng ~15%) | #B0BEC5 | none |

## 7. Khoảng cách & Typography
- Chiều rộng cố định: 220px
- Padding trái nội dung (icon + text): 30px (icon chiếm 16–18px, cách text 8px)
- Chiều cao item: 50px
- Font: Arial 18px, weight 400 (700 cho tiêu đề nhóm nếu có)
- Khoảng cách giữa các nhóm (nếu group): 12px

## 8. Khả năng mở rộng
- Thêm collapse: thu nhỏ sidebar chỉ còn icon (width ~64px) → tooltip hiển thị label.
- Hỗ trợ dark mode: nền #0D47A1, icon/text #FFF, hover #0B3C8C.
- Thêm badge (ví dụ cảnh báo hợp đồng sắp hết hạn): badge hình tròn nền #E53935, text 10–12px.

## 9. Accessibility
- Tab order tuyến tính theo thứ tự items.
- ARIA: role="navigation" cho container; role="menuitem" cho item.
- Tooltip hiển thị label khi ở chế độ collapse.

## 10. Logic kích hoạt (active state)
- So khớp route: nếu route hiện tại bắt đầu bằng item.route thì active (trừ khi item.exact=true).
- Nếu con (children) chứa route đang active → parent mở rộng (expanded=true).

## 11. Quy trình thêm một mục mới
1. Thêm vào `sidebar.config.ts`:
```ts
export const SIDEBAR_ITEMS: SidebarItem[] = [
  // ...existing
  { id: 'reports', label: 'Báo cáo', icon: 'report', route: '/reports', permissions: ['REPORT_VIEW'] }
];
```
2. Thêm icon vào sprite (SVG symbol) hoặc mapping trong `icon.service.ts`.
3. Thêm quyền vào hệ thống RBAC nếu có.
4. Viết test: kiểm tra render + active state.

## 12. Code khung render (pseudo Angular)
```html
<nav class="sidebar" role="navigation">
  <ul>
    <li *ngFor="let item of items" [class.active]="isActive(item)" (click)="go(item)">
      <app-icon [name]="item.icon"></app-icon>
      <span class="label">{{ item.label }}</span>
      <span *ngIf="item.badge" class="badge" [ngClass]="item.badge.type">{{ item.badge.value }}</span>
    </li>
  </ul>
</nav>
```

## 13. SCSS mẫu
```scss
.sidebar { width:220px; background:#1976D2; color:#fff; font-family:Arial; }
.sidebar ul { list-style:none; margin:0; padding:0; }
.sidebar li { height:50px; display:flex; align-items:center; padding:0 12px 0 30px; cursor:pointer; position:relative; }
.sidebar li:hover { background:#1565C0; }
.sidebar li.active { background:#1565C0; }
.sidebar li.active::before { content:''; position:absolute; left:0; top:0; bottom:0; width:3px; background:#FFF; }
.sidebar .badge { margin-left:auto; min-width:18px; height:18px; border-radius:9px; font-size:10px; display:flex; align-items:center; justify-content:center; padding:0 4px; }
.badge.info { background:#0288D1; }
.badge.warn { background:#FBC02D; color:#212121; }
.badge.danger { background:#E53935; }
```

## 14. Kiểm thử (Checklist)
- [ ] Active hiển thị đúng khi điều hướng giữa các trang.
- [ ] Focus ring hiển thị (outline: 2px dashed #FFF hoặc remap theo tiêu chuẩn).
- [ ] Trình đọc màn hình đọc đúng label.
- [ ] Badge không vỡ layout khi giá trị 3 chữ số.

## 15. Chuẩn hoá màu (Design Tokens gợi ý)
| Token | Màu | Công dụng |
|-------|-----|-----------|
| --sb-bg | #1976D2 | nền sidebar |
| --sb-bg-hover | #1565C0 | hover item |
| --sb-bg-active | #1565C0 | active item |
| --sb-text | #FFFFFF | text/icon |
| --sb-badge-warn | #FBC02D | badge cảnh báo |
| --sb-badge-danger | #E53935 | badge lỗi |

## 16. Future Enhancements
- Gắn analytics click từng menu.
- Cho phép drag-drop reorder (user preference).
- Ghim mục ưa thích (favorites) lên đầu.
- Lazy load icon sprite.

---
Last update: 2025-09-25


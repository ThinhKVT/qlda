# HIGH-LEVEL DESIGN HỆ THỐNG QUẢN LÝ DỰ ÁN MUA SẮM ÁP DỤNG LUẬT ĐẤU THẦU

## 1. Kiến trúc tổng thể

- Hệ thống được xây dựng theo mô hình 3 lớp (Three-tier Architecture):
  - **Frontend (Client):** Angular
  - **Backend (Server):** Java Spring Boot (RESTful API)
  - **Database:** PostgreSQL

- Giao tiếp giữa Frontend và Backend thông qua REST API (JSON).
- Hệ thống hỗ trợ triển khai on-premise hoặc cloud (AWS, Azure, GCP).

---

## 2. Sơ đồ tổng quan kiến trúc

```
+-------------------+        HTTPS/REST        +---------------------+        JDBC        +-------------------+
|    Angular App    | <---------------------> |  Spring Boot API    | <---------------> |   PostgreSQL DB   |
+-------------------+                        +---------------------+                  +-------------------+
```

---

## 3. Các thành phần chính

### 3.1. Frontend (Angular)
- Xây dựng SPA (Single Page Application) với Angular.
- Các module chính:
  - Quản lý dự án
  - Quản lý gói thầu
  - Quản lý nhà thầu
  - Quản lý hồ sơ mời thầu/dự thầu
  - Quản lý hợp đồng
  - Quản lý tài liệu
  - Báo cáo, thống kê
  - Quản lý người dùng, phân quyền
  - Thông báo, gửi mail
- Sử dụng Angular Material hoặc PrimeNG cho UI/UX.
- Tích hợp xác thực JWT, phân quyền theo vai trò.
- **Giao diện chi tiết dự án:**
  - Hiển thị thêm trường "Loại kế hoạch" (giá trị: Mới/Bổ sung/Chuyển tiếp) bên phải "Tổng giá trị hợp đồng".
  - Hiển thị thêm trường "Năm kế hoạch" bên phải dải ngày "01/01/2025 - 31/12/2025".
  - Khi số lượng bản ghi tiến độ vượt quá 10, cho phép user scroll để xem các nội dung tiếp theo.
- Quản lý tài liệu: Cho phép tải xuống tất cả tài liệu hoặc chọn 1 hoặc nhiều tài liệu để tải về. Giao diện có checkbox chọn tất cả ở đầu bảng và checkbox từng dòng, các cột dữ liệu căn chỉnh lại cho cân đối với checkbox. Nút "Tải xuống" (màu xanh lá) và "Huỷ chọn" (màu xanh lá) đặt dưới cùng bên trái bảng tài liệu, cho phép tải các tài liệu đã chọn hoặc bỏ chọn tất cả tài liệu.

### 3.2. Backend (Java Spring Boot)
- Cung cấp RESTful API cho frontend và các hệ thống tích hợp.
- Các module/service chính:
  - ProjectService: Quản lý dự án
  - PackageService: Quản lý gói thầu
  - BidderService: Quản lý nhà thầu
  - TenderDocumentService: Quản lý hồ sơ mời thầu/dự thầu
  - ContractService: Quản lý hợp đồng
  - DocumentService: Quản lý tài liệu
  - ReportService: Báo cáo, thống kê
  - UserService: Quản lý người dùng, phân quyền
  - NotificationService: Gửi email, thông báo
  - AuditLogService: Nhật ký hoạt động
- Sử dụng Spring Security cho xác thực, phân quyền (JWT).
- Sử dụng JPA/Hibernate để truy cập PostgreSQL.
- Tích hợp gửi email (JavaMail), xuất/nhập Excel (Apache POI), PDF (iText).
- Lưu trữ file tài liệu trên server hoặc cloud (AWS S3, Azure Blob Storage...)

### 3.3. Database (PostgreSQL)
- Thiết kế các bảng dữ liệu:
  - projects, packages, bidders, tender_documents, contracts, documents, users, roles, permissions, audit_logs, notifications...
- Sử dụng các ràng buộc (constraint), chỉ mục (index), trigger để đảm bảo toàn vẹn dữ liệu.

---

## 4. Luồng hoạt động chính

1. Người dùng đăng nhập vào hệ thống (Angular gửi thông tin đăng nhập đến Spring Boot, xác thực JWT).
2. Người dùng thao tác trên giao diện (quản lý dự án, gói thầu, nhà thầu, hợp đồng, tài liệu...).
3. Angular gửi request đến REST API (Spring Boot), backend xử lý nghiệp vụ, truy vấn/ghi dữ liệu vào PostgreSQL.
4. Khi có sự kiện quan trọng (tạo mới, thay đổi trạng thái...), backend gửi email thông báo qua NotificationService.
5. Người dùng có thể xuất/nhập dữ liệu Excel, PDF từ frontend (gọi API backend xử lý file).
6. Tài liệu được upload/download qua backend, lưu trữ theo từng dự án/gói thầu/giai đoạn.
7. Mọi thao tác được ghi nhận vào AuditLog để truy vết.

---

## 5. Bảo mật & phân quyền
- Xác thực người dùng bằng JWT.
- Phân quyền theo vai trò (admin, cán bộ đấu thầu, lãnh đạo, nhà thầu...).
- Mã hóa dữ liệu nhạy cảm (nếu cần).
- Kiểm soát truy cập tài liệu theo dự án/gói thầu/giai đoạn.

---

## 6. Khả năng mở rộng & tích hợp
- Dễ dàng mở rộng thêm module mới (microservice hoặc monolith module).
- Tích hợp với hệ thống văn bản, tài chính, chữ ký số, SMS, cloud storage.
- Hỗ trợ triển khai CI/CD, logging, monitoring.

---

## 7. Công nghệ sử dụng
- **Frontend:** Angular, Angular Material/PrimeNG, RxJS, JWT
- **Backend:** Java 17+, Spring Boot, Spring Security, Spring Data JPA, JavaMail, Apache POI, iText, MapStruct
- **Database:** PostgreSQL
- **Khác:** Docker, Git, CI/CD (Jenkins/GitHub Actions), Cloud Storage (AWS S3/Azure Blob), Monitoring (Prometheus/Grafana)

---

## 8. Định hướng phát triển tương lai
- Tích hợp mobile app (Ionic/Flutter)
- Tích hợp AI hỗ trợ phân tích dữ liệu, dự báo tiến độ, chi phí
- Tích hợp chữ ký số, xác thực đa yếu tố (MFA)
- Tích hợp hệ thống quản lý văn bản, tài chính

---

### 3.2.1. Trường Loại dự án
- Chỉ cho phép chọn 1 trong 4 giá trị:
  1. Dự án
  2. Phương án mua sắm hàng hoá
  3. Phương án mua sắm dịch vụ
  4. Phương án bảo trì
- Hiển thị dạng dropdown, không cho phép nhập tự do.

### 3.2.2. Quy tắc sinh Mã dự án
- Mã dự án sinh tự động theo loại dự án và năm kế hoạch:
  - Dự án: DA + năm kế hoạch + số thứ tự 3 số (VD: DA2025001)
  - Phương án mua sắm hàng hoá: HH + năm kế hoạch + số thứ tự 3 số (VD: HH2025001)
  - Phương án mua sắm dịch vụ: DV + năm kế hoạch + số thứ tự 3 số (VD: DV2025001)
  - Phương án bảo trì: BT + năm kế hoạch + số thứ tự 3 số (VD: BT2025001)
- Số thứ tự tăng dần theo từng loại dự án trong từng năm kế hoạch, bắt đầu từ 001.
- Backend sẽ sinh mã tự động khi thêm mới dự án thành công.

### 3.2.3. Trường Phòng đầu mối
- Dropdown chỉ cho phép chọn 1 trong 8 giá trị:
  1. Quản trị hạ tầng
  2. An ninh bảo mật
  3. Quản trị ứng dụng
  4. Triển khai ứng dụng
  5. Quản lý và vận hành trung tâm dữ liệu
  6. Vận hành hỗ trợ
  7. Tổ chức hành chính
  8. Kế hoạch tài chính

### 3.2.4. Trường Cán bộ đầu mối
- Là trường lookup, giá trị là ID cán bộ thuộc phòng đầu mối đã chọn.
- Khi nhập liệu, chỉ cho phép tìm kiếm và chọn cán bộ thuộc đúng phòng đầu mối.
- Giao diện nên là ô tìm kiếm có gợi ý (autocomplete/select2), tìm theo tên/mã/email.
- Khi lưu, chỉ lưu ID cán bộ (ví dụ: thinhnv8, lannv1, ...).

### 3.2.5. Trường Cán bộ QLDA
- Là trường lookup, giá trị là ID cán bộ quản lý dự án (chọn từ toàn bộ cán bộ công ty).
- Khi nhập liệu, cho phép tìm kiếm và chọn cán bộ từ danh sách cán bộ toàn công ty (không giới hạn theo phòng).
- Giao diện nên là ô tìm kiếm có gợi ý (autocomplete/select2), tìm theo tên/mã/email.
- Khi lưu, chỉ lưu ID cán bộ (ví dụ: thinhnv8, lannv1, ...).

---

### 3.3. Tính năng Xuất báo cáo
- Cho phép xuất danh sách dự án (toàn bộ hoặc đã lọc/tìm kiếm) ra file Excel.
- Nút "Xuất báo cáo" đặt trên giao diện danh sách dự án, cùng hàng với các nút chức năng khác.
- Khi nhấn, frontend gọi API backend để sinh file Excel với các cột thông tin dự án như trên giao diện.
- API backend nhận tham số lọc/tìm kiếm, trả về file Excel đúng dữ liệu và thứ tự cột.
- Đảm bảo dữ liệu xuất ra đúng với dữ liệu đang hiển thị trên giao diện.

---

# Bổ sung chức năng cho màn hình project_list

## Mục tiêu
Nâng cấp màn hình danh sách dự án với các chức năng tìm kiếm nâng cao, lọc nhanh, sắp xếp, chọn nhiều, xuất báo cáo, hiển thị trạng thái trực quan và liên kết chi tiết dự án.

## Phạm vi
- Giao diện danh sách dự án (project_list)
- Popup tìm kiếm nâng cao
- Popup chọn trường xuất báo cáo

## Chức năng bổ sung
1. **Tìm kiếm nâng cao**: Popup cho phép lọc nhiều trường, mở từ nút "Tìm kiếm nâng cao".
2. **Hiển thị tổng số bản ghi**: Dòng "Hiển thị 1-10/100 bản ghi" phía trên/dưới bảng.
3. **Sắp xếp**: Click tiêu đề cột để sắp xếp, có icon mũi tên thể hiện trạng thái sort.
4. **Lọc nhanh**: Dropdown nhỏ trên đầu bảng cho Loại dự án, Trạng thái, Năm kế hoạch.
5. **Chọn nhiều bản ghi**: Checkbox đầu dòng và đầu bảng, thao tác hàng loạt (Xóa, Xuất báo cáo).
6. **Trạng thái trực quan**: Badge màu, icon cho trạng thái (xanh, xám, đỏ).
7. **Xuất báo cáo nâng cao**: Popup chọn trường xuất Excel, thêm xuất PDF.
8. **Tên dự án là link**: Click mở màn hình chi tiết dự án.

## UI/UX
- Bố cục hợp lý, không chồng lấn.
- Thành phần mới có chú thích rõ ràng trong SVG.
- Đảm bảo khả năng mở rộng và dễ sử dụng cho frontend.

## Lưu ý
- SVG cập nhật là mẫu chuẩn cho frontend và trình bày dự án.

---

## 8. Quy tắc nghiệp vụ tiến độ/giai đoạn theo loại dự án

### 8.1. Các loại dự án và logic giai đoạn tiến độ
- **Dự án:**
  - Khi tạo mới, mặc định có 3 giai đoạn: Xây dựng BCKTKT, Thẩm định và phê duyệt BCKTKT, Quyết toán.
  - Khi thêm mới gói thầu, chèn thêm 1 dòng tên gói thầu và 7 giai đoạn vào giữa "Thẩm định và phê duyệt BCKTKT" và "Quyết toán":
    1. Xây dựng hồ sơ mời thầu
    2. Thẩm định và phê duyệt HSMT
    3. Tổ chức đấu thầu
    4. Thẩm định và phê duyệt KQLCNT
    5. Ký hợp đồng
    6. Triển khai hợp đồng
    7. Nghiệm thu thanh lý hợp đồng
- **Phương án mua sắm hàng hoá:**
  - Khi tạo mới, mặc định có 3 giai đoạn: Xây dựng PAMS, Thẩm định và phê duyệt PAMS, Quyết toán.
  - Khi thêm mới gói thầu, chèn thêm 1 dòng tên gói thầu và 7 giai đoạn vào giữa "Thẩm định và phê duyệt PAMS" và "Quyết toán":
    1. Thẩm định và phê duyệt HSMT
    2. Tổ chức đấu thầu
    3. Thẩm định và phê duyệt KQLCNT
    4. Ký hợp đồng
    5. Triển khai hợp đồng
    6. Nghiệm thu thanh lý hợp đồng
- **Phương án mua sắm dịch vụ:**
  - Khi tạo mới, mặc định có 2 giai đoạn: Xây dựng PAMS, Thẩm định và phê duyệt PAMS (không có Quyết toán).
  - Khi thêm mới gói thầu, chèn thêm 1 dòng tên gói thầu và 7 giai đoạn vào sau "Thẩm định và phê duyệt PAMS":
    1. Thẩm định và phê duyệt HSMT
    2. Tổ chức đấu thầu
    3. Thẩm định và phê duyệt KQLCNT
    4. Ký hợp đồng
    5. Triển khai hợp đồng
    6. Nghiệm thu thanh lý hợp đồng
- **Phương án bảo trì:**
  - Khi tạo mới, mặc định có 2 giai đoạn: Xây dựng PABT, Thẩm định và phê duyệt PABT (không có Quyết toán).
  - Khi thêm mới gói thầu, chèn thêm 1 dòng tên gói thầu và 7 giai đoạn vào sau "Thẩm định và phê duyệt PABT":
    1. Thẩm định và phê duyệt HSMT
    2. Tổ chức đấu thầu
    3. Thẩm định và phê duyệt KQLCNT
    4. Ký hợp đồng
    5. Triển khai hợp đồng
    6. Nghiệm thu thanh lý hợp đồng

### 8.2. Quy tắc giao diện
- Nếu có nhiều gói thầu, giao diện tab Tiến độ sẽ xuất hiện thanh cuộn (scrollbar) để người dùng có thể cuộn xem hết các giai đoạn.
- Logic này được thể hiện rõ trong các file thiết kế SVG (uiux_project_detail.svg, uiux_package_detail.svg).

---

*File này mô tả thiết kế tổng thể (high-level design) cho hệ thống quản lý dự án mua sắm áp dụng Luật Đấu thầu Việt Nam.*

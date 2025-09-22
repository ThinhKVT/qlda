# DETAIL DESIGN HỆ THỐNG QUẢN LÝ DỰ ÁN MUA SẮM ÁP DỤNG LUẬT ĐẤU THẦU

## 1. Quản lý dự án (Project Management)
### 1.1. Mô tả nghiệp vụ
- Thêm, sửa, xóa, tìm kiếm, xem chi tiết dự án.
- Đính kèm tài liệu, theo dõi tiến độ, trạng thái.

### 1.2. Backend API
- `GET /api/projects`
- `GET /api/projects/{id}`
- `POST /api/projects`
- `PUT /api/projects/{id}`
- `DELETE /api/projects/{id}`
- `POST /api/projects/{id}/documents`
- `GET /api/projects/{id}/documents`

### 1.3. UI/UX (SVG Wireframe)
- Danh sách dự án:
  <img src="uiux_project_list.svg" alt="Project List" width="900"/>
- Form thêm/sửa dự án:
  <img src="uiux_project_form.svg" alt="Project Form" width="600"/>
- Trang chi tiết dự án:
  <img src="uiux_project_detail.svg" alt="Project Detail" width="900"/>
  - **Bổ sung:**
    - Hiển thị trường "Loại kế hoạch" (Mới/Bổ sung/Chuyển tiếp) bên phải "Tổng giá trị hợp đồng".
    - Hiển thị trường "Năm kế hoạch" bên phải dải ngày "01/01/2025 - 31/12/2025".
    - Khi số lượng bản ghi tiến độ vượt quá 10, cho phép user scroll để xem các nội dung tiếp theo.
- Thông báo, xác nhận, responsive

---

## 2. Quản lý gói thầu (Procurement Package Management)
### 2.1. Mô tả nghiệp vụ
- Thêm, sửa, xóa, tìm kiếm, xem chi tiết gói thầu thuộc dự án.
- Quản lý thông tin, tài liệu, trạng thái, tiến độ.

### 2.2. Backend API
- `GET /api/projects/{projectId}/packages`
- `GET /api/packages/{id}`
- `POST /api/projects/{projectId}/packages`
- `PUT /api/packages/{id}`
- `DELETE /api/packages/{id}`
- `POST /api/packages/{id}/documents`
- `GET /api/packages/{id}/documents`

### 2.3. UI/UX (SVG Wireframe)
- Danh sách gói thầu:
  <img src="uiux_package_list.svg" alt="Package List" width="900"/>
- Form thêm/sửa gói thầu:
  <img src="uiux_package_form.svg" alt="Package Form" width="600"/>
- Trang chi tiết: (bổ sung SVG nếu cần)

---

## 3. Quản lý nhà thầu (Bidder Management)
### 3.1. Mô tả nghiệp vụ
- Thêm, sửa, xóa, tìm kiếm, xem chi tiết nhà thầu
- Theo dõi lịch sử tham gia, trạng thái dự thầu

### 3.2. Backend API
- `GET /api/bidders`
- `GET /api/bidders/{id}`
- `POST /api/bidders`
- `PUT /api/bidders/{id}`
- `DELETE /api/bidders/{id}`

### 3.3. UI/UX
- Danh sách nhà thầu: bảng, filter, nút thêm mới
- Form thêm/sửa: nhập liệu
- Trang chi tiết: thông tin, lịch sử tham gia thầu

---

## 4. Quản lý hồ sơ mời thầu/dự thầu (Tender Document Management)
### 4.1. Mô tả nghiệp vụ
- Tạo, lưu trữ, cập nhật HSMT, HSDT cho từng gói thầu
- Theo dõi lịch sử phát hành, nhận hồ sơ, mở thầu

### 4.2. Backend API
- `POST /api/packages/{id}/tender-documents`
- `GET /api/packages/{id}/tender-documents`
- `PUT /api/tender-documents/{id}`
- `DELETE /api/tender-documents/{id}`

### 4.3. UI/UX
- Danh sách hồ sơ: bảng, filter
- Form thêm/sửa: nhập liệu, upload file

---

## 5. Quản lý hợp đồng (Contract Management)
### 5.1. Mô tả nghiệp vụ
- Thêm, sửa, xóa, tìm kiếm, xem chi tiết hợp đồng của từng gói thầu

### 5.2. Backend API
- `GET /api/packages/{id}/contracts`
- `GET /api/contracts/{id}`
- `POST /api/packages/{id}/contracts`
- `PUT /api/contracts/{id}`
- `DELETE /api/contracts/{id}`

### 5.3. UI/UX
- Danh sách hợp đồng: bảng, filter
- Form thêm/sửa: nhập liệu, upload file
- Trang chi tiết: thông tin, tiến độ, lịch sử thanh toán

---

## 6. Quản lý tài liệu (Document Management)
### 6.1. Mô tả nghiệp vụ
- Lưu trữ, phân loại, tìm kiếm tài liệu theo dự án, gói thầu, hợp đồng, từng giai đoạn
- Cho phép tải xuống tất cả tài liệu hoặc chọn 1 hoặc nhiều tài liệu để tải về:
  - Giao diện có checkbox chọn tất cả ở đầu bảng và checkbox từng dòng để chọn nhiều tài liệu.
  - Nút "Tải xuống" (màu xanh lá) đặt dưới cùng bên trái bảng, cho phép tải các tài liệu đã chọn.
  - Nút "Huỷ chọn" (màu xanh lá) bên phải nút "Tải xuống" để bỏ chọn tất cả tài liệu.
  - Các cột dữ liệu được căn chỉnh lại cho cân đối với checkbox.

### 6.2. Backend API
- `POST /api/documents/upload`
- `GET /api/documents?projectId=&packageId=&contractId=&type=`
- `DELETE /api/documents/{id}`
- `POST /api/documents/download` (hỗ trợ tải nhiều file cùng lúc)

### 6.3. UI/UX
- Danh sách tài liệu: bảng, filter, checkbox chọn nhiều
- Upload tài liệu: chọn file, loại, liên kết đối tượng
- Tải xuống: chọn nhiều hoặc tất cả, nút tải xuống và huỷ chọn dưới bảng

---

## 7. Báo cáo, thống kê (Reporting & Analytics)
### 7.1. Mô tả nghiệp vụ
- Báo cáo tiến độ, kết quả lựa chọn nhà thầu, tổng hợp chi phí, giá trị hợp đồng, thanh toán

### 7.2. Backend API
- `GET /api/reports/progress`
- `GET /api/reports/bidding-results`
- `GET /api/reports/contract-values`
- `GET /api/reports/payment-summary`
- `GET /api/reports/bidder-history`

### 7.3. UI/UX
- Trang báo cáo: chọn loại, filter, bảng, biểu đồ, xuất Excel/PDF

---

## 8. Quản lý người dùng, phân quyền (User & Permission Management)
### 8.1. Mô tả nghiệp vụ
- Quản lý tài khoản, vai trò, phân quyền truy cập

### 8.2. Backend API
- `GET /api/users`
- `GET /api/users/{id}`
- `POST /api/users`
- `PUT /api/users/{id}`
- `DELETE /api/users/{id}`
- `GET /api/roles`
- `POST /api/roles`
- `PUT /api/roles/{id}`
- `DELETE /api/roles/{id}`

### 8.3. UI/UX
- Danh sách người dùng: bảng, filter
- Form thêm/sửa: nhập liệu
- Quản lý vai trò, phân quyền: bảng, gán quyền

---

## 9. Thông báo, gửi mail (Notification & Email)
### 9.1. Mô tả nghiệp vụ
- Gửi email tự động khi có sự kiện quan trọng

### 9.2. Backend API
- `POST /api/notifications/send`
- `GET /api/notifications?userId=`

### 9.3. UI/UX
- Danh sách thông báo: bảng, filter
- Cấu hình email: form cấu hình SMTP, test gửi mail

---

## 10. Nhật ký hoạt động (Audit Log)
### 10.1. Mô tả nghiệp vụ
- Ghi nhận lịch sử thao tác của người dùng

### 10.2. Backend API
- `GET /api/audit-logs?userId=&actionType=&dateFrom=&dateTo=`

### 10.3. UI/UX
- Danh sách nhật ký: bảng, filter

---

## 11. Luồng tổng thể UI/UX
- Menu chính: Dự án | Gói thầu | Nhà thầu | Hồ sơ mời thầu | Hợp đồng | Tài liệu | Báo cáo | Người dùng | Thông báo | Nhật ký
- Breadcrumb, thông báo realtime, đa ngôn ngữ, đăng nhập/đăng xuất

---

# Bổ sung các chức năng UI/UX cho màn hình project_list

## 1. Tìm kiếm nâng cao (Advanced Search)
- Nút "Tìm kiếm nâng cao" mở popup tìm kiếm nâng cao.
- Popup cho phép lọc theo: Loại dự án, Phòng đầu mối, Năm kế hoạch, Trạng thái, Cán bộ đầu mối, Cán bộ QLDA, Ngày bắt đầu, Ngày kết thúc.

## 2. Hiển thị tổng số bản ghi và số bản ghi đang xem
- Thêm dòng thông tin: “Hiển thị 1-10/100 bản ghi” phía trên hoặc dưới bảng.

## 3. Chức năng sắp xếp (Sort)
- Click vào tiêu đề cột để sắp xếp tăng/giảm theo các trường: Tên dự án, Năm kế hoạch, Trạng thái, Ngày bắt đầu, Ngày kết thúc.
- Thêm icon mũi tên lên/xuống ở tiêu đề cột khi sắp xếp.

## 4. Chức năng lọc nhanh (Quick Filter)
- Thêm các dropdown nhỏ trên đầu bảng cho các trường: Loại dự án, Trạng thái, Năm kế hoạch.

## 5. Chức năng chọn nhiều bản ghi (Multi-select)
- Thêm checkbox đầu mỗi dòng và checkbox tổng ở đầu bảng để chọn nhiều dự án.
- Thêm nút thao tác hàng loạt (Xóa nhiều, Xuất báo cáo theo lựa chọn).

## 6. Trạng thái hiển thị rõ ràng hơn
- Sử dụng màu sắc, icon hoặc badge cho trường Trạng thái (Đang thực hiện - xanh, Đã hoàn thành - xám, Quá hạn - đỏ).

## 7. Chức năng xuất báo cáo nâng cao
- Cho phép chọn các trường muốn xuất ra file Excel (popup chọn cột).
- Thêm tuỳ chọn xuất PDF.

## 8. Click vào tên dự án mở màn hình project_detail
- Tên dự án là link (underline + pointer), click mở màn hình chi tiết dự án.

---

### Ghi chú:
- Các thành phần UI mới sẽ được thể hiện rõ ràng trên SVG, có chú thích giải thích.
- Đảm bảo layout hợp lý, không chồng lấn, các nút và filter hợp lý.

---

## 2.2.1. Trường Loại dự án
- Trường "Loại dự án" chỉ cho phép chọn 1 trong 4 giá trị:
  1. Dự án
  2. Phương án mua sắm hàng hoá
  3. Phương án mua sắm dịch vụ
  4. Phương án bảo trì
- Trên giao diện, trường này sẽ hiển thị dạng dropdown (select box), không cho phép nhập tự do hoặc chọn nhiều giá trị.

## 2.2.2. Quy tắc sinh Mã dự án
- Mã dự án được sinh tự động khi thêm mới thành công dự án, theo quy tắc:
  - Đối với loại "Dự án": DA + năm kế hoạch + số thứ tự 3 số (bắt đầu từ 001)
    - Ví dụ: DA2025001, DA2025002, ...
  - Đối với "Phương án mua sắm hàng hoá": HH + năm kế hoạch + số thứ tự 3 số
    - Ví dụ: HH2025001, HH2025002, ...
  - Đối với "Phương án mua sắm dịch vụ": DV + năm kế hoạch + số thứ tự 3 số
    - Ví dụ: DV2025001, DV2025002, ...
  - Đối với "Phương án bảo trì": BT + năm kế hoạch + số thứ tự 3 số
    - Ví dụ: BT2025001, BT2025002, ...
- Số thứ tự sẽ tăng dần theo từng loại dự án trong từng năm kế hoạch, bắt đầu từ 001.
- Khi thêm mới, backend sẽ kiểm tra số lượng dự án đã có cùng loại và năm kế hoạch, sau đó sinh mã tiếp theo.
- Trường "Mã dự án" sẽ được sinh tự động và chỉ hiển thị sau khi thêm mới thành công (hoặc readonly nếu muốn hiển thị trước khi lưu).

## 2.2.3. Trường Phòng đầu mối
- Trường "Phòng đầu mối" chỉ cho phép chọn 1 trong 8 giá trị:
  1. Quản trị hạ tầng
  2. An ninh bảo mật
  3. Quản trị ứng dụng
  4. Triển khai ứng dụng
  5. Quản lý và vận hành trung tâm dữ liệu
  6. Vận hành hỗ trợ
  7. Tổ chức hành chính
  8. Kế hoạch tài chính
- Hiển thị dạng dropdown (select box), không cho phép nhập tự do hoặc chọn nhiều giá trị.

## 2.2.4. Trường Cán bộ đầu mối
- Trường "Cán bộ đầu mối" là trường lookup, giá trị là ID của cán bộ thuộc phòng đầu mối đã chọn.
- Khi nhập liệu, người dùng tìm kiếm và chọn cán bộ từ danh sách cán bộ của công ty, chỉ hiển thị các cán bộ thuộc đúng phòng đầu mối đã chọn.
- Giao diện nên sử dụng ô tìm kiếm có gợi ý (autocomplete/select2), cho phép tìm kiếm theo tên, mã hoặc email cán bộ.
- Khi lưu, chỉ lưu ID cán bộ (ví dụ: thinhnv8, lannv1, ...).

## 2.2.5. Trường Cán bộ QLDA
- Trường "Cán bộ QLDA" là trường lookup, giá trị là ID của cán bộ quản lý dự án (cán bộ thuộc toàn công ty).
- Khi nhập liệu, người dùng tìm kiếm và chọn cán bộ từ danh sách cán bộ của toàn công ty (không giới hạn theo phòng).
- Giao diện nên sử dụng ô tìm kiếm có gợi ý (autocomplete/select2), cho phép tìm kiếm theo tên, mã hoặc email cán bộ.
- Khi lưu, chỉ lưu ID cán bộ (ví dụ: thinhnv8, lannv1, ...).

---

## 2.3. Tính năng Xuất báo cáo
- Cho phép người dùng xuất danh sách tất cả các dự án hoặc danh sách dự án sau khi tìm kiếm ra file Excel.
- Nút "Xuất báo cáo" hiển thị trên giao diện danh sách dự án, nằm cùng hàng với các nút chức năng khác.
- Khi nhấn, hệ thống sẽ xuất file Excel với các cột tương ứng thông tin dự án đang hiển thị (STT, Mã dự án, Tên dự án, Loại dự án, Phòng đầu mối, Cán bộ đầu mối, Cán bộ QLDA, Năm kế hoạch, Trạng thái, Ngày bắt đầu, Ngày kết thúc, ...).
- Backend cung cấp API xuất Excel, nhận các tham số lọc/tìm kiếm hiện tại.
- File Excel sinh ra đúng định dạng, dữ liệu, thứ tự cột như trên giao diện.
- Đảm bảo xuất đúng dữ liệu đã lọc/tìm kiếm, không bị thiếu hoặc dư bản ghi.

---

## 6. Quy tắc nghiệp vụ tiến độ/giai đoạn theo loại dự án

### 6.1. Các loại dự án và logic giai đoạn tiến độ
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

### 6.2. Quy tắc giao diện
- Nếu có nhiều gói thầu, giao diện tab Tiến độ sẽ xuất hiện thanh cuộn (scrollbar) để người dùng có thể cuộn xem hết các giai đoạn.
- Logic này được thể hiện rõ trong các file thiết kế SVG (uiux_project_detail.svg, uiux_package_detail.svg).

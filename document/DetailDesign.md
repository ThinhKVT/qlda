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
- Form cập nhật hợp đồng & tiến độ cho giai đoạn "Ký hợp đồng" (SVG: `uiux_qlda/menu_project/uiux_contract_edit_form.svg`)
  - Hiển thị khi người dùng nhấn nút "Chỉnh sửa" tại giai đoạn "Ký hợp đồng" trong tab Tiến độ của `project_detail` hoặc khi chuyển trạng thái sang "Đã hoàn thành" mà thiếu thông tin.
  - Trường bắt buộc của hợp đồng: Tên hợp đồng (mặc định theo tên gói thầu), Số hợp đồng, Ngày ký hợp đồng, Ngày hiệu lực hợp đồng, Thời gian thực hiện (tháng), Giá trị hợp đồng (nhập số + dropdown đơn vị VND/USD), Nhà thầu (textbox search + gợi ý), Số đợt thanh toán (nhập số ≥ 1).
  - Trường tự động: Ngày hết hiệu lực = Ngày hiệu lực + Thời gian thực hiện; Loại hợp đồng lấy tự động từ CSDL (readonly).
  - Nhóm Tiến độ: Dropdown Trạng thái gồm: Chưa thực hiện, Đang thực hiện, Đã hoàn thành, Huỷ thực hiện.
    - Quy tắc: Muốn chuyển từ "Chưa thực hiện" sang "Đang thực hiện" phải nhập "Ngày bắt đầu" và "Ngày kết thúc".
    - Khi chọn trạng thái "Đã hoàn thành" thì bắt buộc nhập "Ngày hoàn thành".
    - "Chi tiết tiến độ" là trường không bắt buộc.

### 5.4. Đồng bộ dữ liệu thật (Tab "Thông tin hợp đồng" của gói thầu)
- API khuyến nghị:
  - GET `GET /api/packages/{id}/contract` → trả về thông tin hợp đồng của gói thầu
  - POST/PUT `POST /api/packages/{id}/contract` | `PUT /api/contracts/{id}` → lưu/cập nhật khi user hoàn tất form "Ký hợp đồng"
- JSON mẫu (response GET /api/packages/{id}/contract):
```json
{
  "packageId": "PKG-001",
  "name": "Tên gói thầu", // Dùng hiển thị cho "Tên hợp đồng"
  "number": "HD-2025/001",
  "signedDate": "2025-05-23",
  "effectiveDate": "2025-05-23",
  "durationMonths": 36,
  "expireDate": "2028-05-23",
  "value": 1800000000,
  "currency": "VND", // hoặc USD
  "bidderId": "NT-XYZ",
  "bidderName": "Tổng công ty Xây dựng XYZ",
  "type": "Trọn gói",
  "paymentInstallments": 6
}
```
- Mapping hiển thị (readonly ở tab):
  - Tên hợp đồng = contract.name (lấy theo tên gói thầu) → không kèm số HĐ
  - Số hợp đồng = contract.number
  - Ngày ký = contract.signedDate (format dd/MM/yyyy)
  - Loại hợp đồng = contract.type
  - Ngày hiệu lực = contract.effectiveDate (format dd/MM/yyyy)
  - Thời gian (tháng) = contract.durationMonths
  - Ngày hết hiệu lực = contract.expireDate (format dd/MM/yyyy), tính tự động từ effectiveDate + durationMonths khi lưu
  - Giá trị hợp đồng + đơn vị = contract.value + contract.currency
  - Nhà thầu = contract.bidderName
  - Số đợt thanh toán = contract.paymentInstallments

### 5.5. Quy tắc định dạng & xử lý hiển thị
- Định dạng ngày:
  - Input/Output chuẩn: dd/MM/yyyy. Khi nhận ISO-8601 từ API (YYYY-MM-DD), hiển thị dưới dạng dd/MM/yyyy.
- Định dạng tiền:
  - VND: phân tách nghìn bằng dấu chấm, không phần thập phân: ví dụ 1.800.000.000 VND
  - USD: phân tách nghìn bằng dấu phẩy, tối đa 2 chữ số thập phân: ví dụ 1,234,567.89 USD
- Xử lý tên gói thầu/"Tên hợp đồng" dài:
  - Cho phép tự xuống dòng (wrap) thay vì cắt bớt (ellipsis). Trên web, dùng CSS: `white-space: normal; word-break: break-word;`.

### 5.6. Tab "Thanh toán" của gói thầu
- Mục tiêu: quản lý các đợt thanh toán theo "Số đợt thanh toán" đã khai báo trong hợp đồng.
- (Cập nhật) Khi hiển thị bảng cần phân biệt rõ:
  - Trạng thái = `Chưa thanh toán` (PENDING): cột "Ngày thanh toán", "Giá trị (VND)" hiển thị giá trị DỰ KIẾN (planned).
  - Trạng thái = `Đã thanh toán` (PAID): cột "Ngày thanh toán", "Giá trị (VND)" hiển thị giá trị THỰC TẾ (actual) đã được xác nhận.
- Yêu cầu nhập bắt buộc khi thêm/cập nhật 1 đợt ở chế độ dự kiến:
  - Đợt (auto: Đợt 1, Đợt 2, ...), Ngày thanh toán dự kiến (plannedPayDate), Giá trị dự kiến (plannedAmount), Trạng thái, Hồ sơ thanh toán (có thể cho phép tải trước hoặc bắt buộc sau), (Số chứng từ chỉ bắt buộc khi trạng thái chuyển sang Đã thanh toán), Ghi chú (không bắt buộc).
- Ràng buộc tổng quan:
  - Tổng giá trị (dùng actualAmount nếu đã paid; nếu chưa thì dùng plannedAmount) của tất cả đợt ≤ Giá trị hợp đồng.
  - Khi trạng thái chuyển sang "Đã thanh toán" phải có: Ngày thanh toán thực tế (actualPayDate), Giá trị thanh toán thực tế (actualAmount), Số chứng từ (voucherNo), Ít nhất 1 file Hồ sơ thanh toán.
- Thêm cột "Hành động" (View | Edit | Delete) trong bảng (SVG cập nhật: `uiux_package_detail_payment.svg`).
- Modal chuyển trạng thái sang "Đã thanh toán": SVG `uiux_package_payment_update_form.svg`.
- Modal chỉnh sửa lại (re-open) sau khi đã thanh toán: SVG `uiux_package_payment_update_form_reopen.svg`.
- Trạng thái hiển thị sau khi đã thanh toán (style hàng màu nhấn / trạng thái xanh): SVG `uiux_package_detail_payment_paid_state.svg`.

#### 5.6.1. Phân tách dữ liệu dự kiến & thực tế (Model đề xuất)
| Trường | Mô tả |
|--------|-------|
| plannedPayDate | Ngày dự kiến thanh toán ban đầu |
| plannedAmount | Giá trị dự kiến thanh toán |
| actualPayDate | Ngày thanh toán thực tế (null nếu chưa) |
| actualAmount | Giá trị thanh toán thực tế (null nếu chưa) |
| status | PENDING hoặc PAID |
| voucherNo | Bắt buộc khi PAID |
| attachments | Danh sách file đính kèm (>=1 khi PAID) |
| note | Ghi chú tuỳ chọn |

Hiển thị bảng:
- Cột Ngày thanh toán = (status==PAID ? actualPayDate : plannedPayDate)
- Cột Giá trị (VND) = (status==PAID ? actualAmount : plannedAmount)
- Cột Tỷ lệ (%) = (status==PAID ? actualAmount : plannedAmount) / contract.value * 100 (round 2)

#### 5.6.2. Flow chuyển trạng thái
1. User đổi dropdown trạng thái dòng từ PENDING → PAID.
2. Frontend chặn thay đổi trực tiếp, bật modal `uiux_package_payment_update_form.svg`.
3. User nhập đủ 4 trường bắt buộc: actualPayDate, actualAmount (>0), voucherNo, attachments≥1.
4. Validate tổng mới (tổng actual cho các dòng PAID + planned/actual của dòng hiện tại ≤ contract.value).
5. Gửi API cập nhật: PUT /api/payments/{id} kèm trường actual*. BE set status=PAID.
6. Thành công → cập nhật lại bảng (row cập nhật; style status xanh #2E7D32; hiển thị text "Đã đính kèm (n)" nếu muốn).
7. Re-open edit (icon Edit ở hàng PAID) → mở modal `uiux_package_payment_update_form_reopen.svg` (prefill actual data).

#### 5.6.3. Delete / Edit ràng buộc
- Không cho xoá đợt đã thanh toán (status=PAID) (có thể mở rộng logic rollback kèm quyền đặc biệt).
- Sửa đợt PAID: Cho phép chỉnh Số chứng từ, ghi chú, bổ sung/xoá file, (tuỳ chính sách có cho sửa actualAmount hay không – nếu sửa phải re-validate tổng và ghi Audit Log).
- Sửa đợt PENDING: Cho phép đổi plannedPayDate, plannedAmount, note.

#### 5.6.4. Icon Hành động
- View: mở panel hoặc popup hiển thị chi tiết + danh sách file.
- Edit: logic như mô tả (phụ thuộc trạng thái).
- Delete: confirm trước khi xoá (nếu PENDING).

### 5.8. Frontend - Hướng dẫn triển khai tab Thanh toán & helpers (Cập nhật)
...existing code...
- Thêm helpers:
  - `computeDisplayDate(payment)` → return actualPayDate || plannedPayDate.
  - `computeDisplayAmount(payment)` → return actualAmount || plannedAmount.
  - `computeRatio(payment, contractValue)` → round( computeDisplayAmount(payment)/contractValue * 100, 2 ).
- State chuyển đổi:
  - Tách form validation cho planned vs actual.
  - Modal Submit disable nếu thiếu bất kỳ trường bắt buộc ở chế độ xác nhận thực tế.

### 5.9. Backend - Payments API (Cập nhật model & validation)
...existing code...
Model mới gợi ý:
```json
{
  "id": "PM-001",
  "contractId": "CT-001",
  "installmentNo": 1,
  "plannedPayDate": "2025-10-01",
  "plannedAmount": 300000000,
  "actualPayDate": "2025-10-15",
  "actualAmount": 300000000,
  "status": "PENDING|PAID",
  "voucherNo": "PT-0002",
  "note": "Đợt bảo hành",
  "attachments": [
    { "id": "DOC-1", "name": "hs_thanhtoan_2.pdf", "url": "/docs/..." }
  ],
  "createdAt": "2025-09-01T10:00:00Z",
  "updatedAt": "2025-10-16T09:12:33Z"
}
```
Validation cập nhật:
- `plannedAmount >=0`; `actualAmount > 0` khi status=PAID.
- Khi status=PAID: `actualPayDate` not null, `voucherNo` not blank, `attachments.length > 0`.
- Tổng `Σ(each status==PAID ? actualAmount : plannedAmount)` ≤ contract.value.
- Nếu cho phép điều chỉnh actualAmount sau khi PAID: ghi AuditLog (oldValue, newValue, userId, timestamp).

Error codes bổ sung:
- `MISSING_ACTUAL_FIELDS`
- `PAYMENT_ALREADY_PAID_NO_DELETE`
- `ACTUAL_ADJUST_EXCEEDS_CONTRACT_VALUE`
- `INVALID_TRANSITION` (ví dụ PENDING→PAID thiếu data)

### 5.9.1 Endpoint mở rộng (gợi ý)
- `PUT /api/payments/{id}/confirm` → chuyên biệt dùng confirm thanh toán (server enforce transition & validation).
- `PUT /api/payments/{id}/adjust` → dùng khi chỉnh sửa giá trị thực tế (có quyền). Ghi AuditLog.
- `GET /api/payments/{id}/history` → audit lịch sử thay đổi (nếu cần trace planned vs actual).

### 5.10. Tài liệu - Enum loại & mapping hành động API (Cập nhật)
- Bổ sung loại tài liệu: `PAYMENT_SUPPORT` (hồ sơ thanh toán) nếu muốn tách với `PAYMENT_VOUCHER`.
- Khi upload hồ sơ ở modal xác nhận thanh toán: type = `PAYMENT_SUPPORT`.

### 5.11. Audit Log (Bổ sung cho thanh toán)
- Ghi các sự kiện:
  - CREATE_PAYMENT (planned)
  - UPDATE_PAYMENT_PLANNED_FIELDS
  - CONFIRM_PAYMENT_ACTUAL (chuyển PENDING→PAID)
  - ADJUST_PAYMENT_ACTUAL
  - ADD_PAYMENT_ATTACHMENT / REMOVE_PAYMENT_ATTACHMENT

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

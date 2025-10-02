# U-PAYMENTS — Thanh toán theo đợt (Payments)

Phạm vi
- Tab "Thanh toán" của gói thầu; quản lý planned vs actual; confirm PENDING→PAID; không cho phép sửa actualAmount sau khi đã PAID; ràng buộc tổng ≤ giá trị hợp đồng; audit sự kiện thanh toán.

User Stories & AC

1) PROJ-09 — Tab "Thanh toán" của gói thầu
- SVG: ../uiux_qlda/menu_project/uiux_package_detail_payment.svg
  - Popup lần đầu: ../uiux_qlda/menu_project/uiux_package_payment_update_form.svg
  - Popup reopen: ../uiux_qlda/menu_project/uiux_package_payment_update_form_reopen.svg
  - Trạng thái PAID: ../uiux_qlda/menu_project/uiux_package_detail_payment_paid_state.svg
- AC:
  - "Thêm đợt": auto tăng số đợt; "Sinh theo số đợt": dựa paymentInstallments.
  - PENDING→PAID: bắt buộc actualPayDate, actualAmount>0, voucherNo, ≥1 file; xác nhận qua popup lần đầu.
  - Reopen: cho phép sửa non-financial fields (voucherNo, note, attachments); không cho phép sửa actualAmount sau khi PAID.
  - Tổng các đợt (actual với PAID; planned với PENDING) ≤ giá trị hợp đồng; validate khi lưu/chuyển trạng thái.
  - Bảng hiển thị ngày/giá trị theo status; tính tỷ lệ % với 2 chữ số sau dấu phẩy.

2) PAY-API-01 — API Thanh toán theo đợt
- AC:
  - Cung cấp endpoints CRUD payments; endpoint confirm để chuyển PENDING→PAID; endpoint adjust (nếu có chính sách tương lai) mặc định bị khóa (không cho sửa actualAmount hiện tại).
  - Trả mã lỗi phù hợp: MISSING_ACTUAL_FIELDS, ACTUAL_ADJUST_EXCEEDS_CONTRACT_VALUE, INVALID_TRANSITION, PAYMENT_ALREADY_PAID_NO_DELETE.

3) LOG-01 — Audit Log (liên quan payments)
- AC:
  - Ghi các sự kiện: CREATE_PAYMENT, UPDATE_PAYMENT_PLANNED_FIELDS, CONFIRM_PAYMENT_ACTUAL, ADD/REMOVE_ATTACHMENT.
  - Lưu userId (từ JWT), thời điểm, old/new values khi phù hợp.

Phụ thuộc
- U-PROJECT: Lấy contract.value & paymentInstallments.
- U-DOCS: Upload hồ sơ thanh toán; multi-download nếu cần xem file.
- U-IDENT: JWT/roles.
- U-PLATFORM: OpenAPI, DB, CI.


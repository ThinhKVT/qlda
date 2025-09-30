# YÊU CẦU NGHIỆP VỤ PHẦN MỀM QUẢN LÝ DỰ ÁN MUA SẮM ÁP DỤNG LUẬT ĐẤU THẦU VIỆT NAM
Luồng chức năng theo menu (UI/UX chốt theo SVG)

Mục tiêu: Chuẩn hoá yêu cầu nghiệp vụ theo các luồng người dùng thực tế trên giao diện đã chốt. Mỗi luồng bên dưới tham chiếu trực tiếp tới các file SVG thiết kế trong thư mục `uiux_qlda/`.

### 15.1. Menu Dự án
- Danh sách dự án: hiển thị theo bố cục và cột đúng như SVG.
  - Màn hình: `uiux_project_list.svg`
  - Ảnh tham chiếu: <img src="../uiux_qlda/menu_project/uiux_project_list.svg" alt="Project List" width="900"/>
- Thêm mới dự án: nhấn "Thêm mới" mở popup form.
  - Popup: `uiux_project_form.svg`
  - Ảnh tham chiếu: <img src="../uiux_qlda/menu_project/uiux_project_form.svg" alt="Project Form" width="650"/>
- Tìm kiếm nâng cao: nhấn "Tìm kiếm nâng cao" mở popup.
  - Popup: `uiux_project_advanced_search_popup.svg`
  - Ảnh tham chiếu: <img src="../uiux_qlda/menu_project/uiux_project_advanced_search_popup.svg" alt="Advanced Search" width="650"/>
- Xem chi tiết dự án: click hành động "Xem" hoặc tên dự án.
  - Mặc định mở tab "Tiến độ". Ở mỗi bản ghi giai đoạn có nút "Chỉnh sửa" mở popup chỉnh sửa.
  - Riêng giai đoạn "Ký hợp đồng": nhấn "Chỉnh sửa" mở popup `uiux_contract_edit_form.svg`.
  - Màn hình chi tiết: <img src="../uiux_qlda/menu_project/uiux_project_detail.svg" alt="Project Detail" width="900"/>
  - Popup hợp đồng: <img src="../uiux_qlda/menu_project/uiux_contract_edit_form.svg" alt="Contract Edit Form" width="700"/>
- Tab "Gói thầu": mở danh sách gói thầu thuộc dự án.
  - Màn hình: `uiux_package_list.svg` → <img src="../uiux_qlda/menu_project/uiux_package_list.svg" alt="Package List" width="900"/>
  - "Thêm gói thầu": mở `uiux_package_form.svg` → <img src="../uiux_qlda/menu_project/uiux_package_form.svg" alt="Package Form" width="650"/>
  - Hành động "Xem": mở `uiux_package_detail.svg` (mặc định tab "Thông tin hợp đồng").
    - <img src="../uiux_qlda/menu_project/uiux_package_detail.svg" alt="Package Detail" width="900"/>
    - Tab "Thanh toán": `uiux_package_detail_payment.svg` → <img src="../uiux_qlda/menu_project/uiux_package_detail_payment.svg" alt="Package Payment" width="900"/>
      - "Thêm đợt": tự động thêm 1 bản ghi mới với số đợt tăng dần.
      - "Sinh theo số đợt": tự động sinh các đợt theo số đợt thanh toán khai báo ở tab Thông tin hợp đồng.
      - "Chỉnh sửa" lần đầu: mở `uiux_package_payment_update_form.svg` → <img src="../uiux_qlda/menu_project/uiux_package_payment_update_form.svg" alt="Payment Update Form" width="650"/>
      - "Chỉnh sửa" từ lần 2: mở `uiux_package_payment_update_form_reopen.svg` → <img src="../uiux_qlda/menu_project/uiux_package_payment_update_form_reopen.svg" alt="Payment Update Form Reopen" width="650"/>
      - Trạng thái sau khi đã thanh toán: thể hiện như `uiux_package_detail_payment_paid_state.svg` → <img src="../uiux_qlda/menu_project/uiux_package_detail_payment_paid_state.svg" alt="Payment Paid State" width="900"/>
    - Tab "Tài liệu": `uiux_package_detail_documents.svg` → <img src="../uiux_qlda/menu_project/uiux_package_detail_documents.svg" alt="Package Documents" width="900"/>
- Tab "Nhân sự": `uiux_project_personnel.svg` → <img src="../uiux_qlda/menu_project/uiux_project_personnel.svg" alt="Project Personnel" width="900"/>
  - "Thêm nhân sự": `uiux_add_personnel_form.svg` → <img src="../uiux_qlda/menu_project/uiux_add_personnel_form.svg" alt="Add Personnel Form" width="650"/>
- Tab "Tài liệu": `uiux_project_document.svg` → <img src="../uiux_qlda/menu_project/uiux_project_document.svg" alt="Project Document" width="900"/>
- Tab "Quyết toán": `uiux_project_settlement.svg` → <img src="../uiux_qlda/menu_project/uiux_project_settlement.svg" alt="Project Settlement" width="900"/>
  - "Sửa": `uiux_project_settlement_edit_popup.svg` → <img src="../uiux_qlda/menu_project/uiux_project_settlement_edit_popup.svg" alt="Settlement Edit" width="650"/>
  - "Upload": `uiux_project_settlement_upload_popup.svg` → <img src="../uiux_qlda/menu_project/uiux_project_settlement_upload_popup.svg" alt="Settlement Upload" width="650"/>

### 15.2. Menu Hợp đồng
- Click menu Hợp đồng: hiển thị danh sách hợp đồng `uiux_contract_list.svg` → <img src="../uiux_qlda/menu_hopdong/uiux_contract_list.svg" alt="Contract List" width="900"/>
- Hành động "Xem": mở popup `uiux_package_contract_info.svg` → <img src="../uiux_qlda/menu_hopdong/uiux_package_contract_info.svg" alt="Package Contract Info" width="700"/>

### 15.3. Menu Nhà thầu
- Click menu Nhà thầu: danh sách `uiux_contractor_list.svg` → <img src="../uiux_qlda/menu_nhathau/uiux_contractor_list.svg" alt="Contractor List" width="900"/>
- "Thêm nhà thầu": popup `uiux_contractor_add.svg` → <img src="../uiux_qlda/menu_nhathau/uiux_contractor_add.svg" alt="Contractor Add" width="650"/>
- Hành động "Xem": popup `uiux_contractor_detail_popup.svg` → <img src="../uiux_qlda/menu_nhathau/uiux_contractor_detail_popup.svg" alt="Contractor Detail" width="700"/>

### 15.4. Menu Kho tri thức
- Click menu Kho tri thức: `uiux_knowledge_base.svg` → <img src="../uiux_qlda/menu_khotrithuc/uiux_knowledge_base.svg" alt="Knowledge Base" width="900"/>
- "Thêm tài liệu": popup `uiux_knowledge_base_add_document.svg` → <img src="../uiux_qlda/menu_khotrithuc/uiux_knowledge_base_add_document.svg" alt="KB Add Document" width="650"/>

### 15.5. Menu Dashboard
- Click menu Dashboard: `uiux_dashboard.svg` → <img src="../uiux_qlda/dashboard/uiux_dashboard.svg" alt="Dashboard" width="900"/>
- Dữ liệu hiển thị tổng hợp từ cơ sở dữ liệu đã nhập tại các màn hình liên quan.

---

*Phần 15 đảm bảo tài liệu nghiệp vụ bám sát các tương tác UI/UX đã chốt, giúp thống nhất phạm vi và hành vi hệ thống.*

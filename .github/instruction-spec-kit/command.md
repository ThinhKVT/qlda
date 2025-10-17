| Lệnh                    | Mô tả                                               |
| ----------------------- | --------------------------------------------------- |
| `/speckit.constitution` | Thiết lập nguyên tắc và định hướng cho dự án        |
| `/speckit.specify`      | Tạo đặc tả cơ bản (baseline specification)          |
| `/speckit.plan`         | Lập kế hoạch triển khai                             |
| `/speckit.tasks`        | Tạo danh sách các nhiệm vụ cụ thể, có thể hành động |
| `/speckit.implement`    | Tiến hành thực thi việc triển khai                  |

⚙️ Các lệnh tùy chọn (giúp nâng cao chất lượng và độ tin cậy)

| Lệnh                              | Mô tả                                                           | Thời điểm khuyên dùng                            |
| --------------------------------- | --------------------------------------------------------------- | ------------------------------------------------ |
| `/speckit.clarify` *(tùy chọn)*   | Đặt câu hỏi có cấu trúc để làm rõ những điểm mơ hồ, giảm rủi ro | Trước khi chạy `/speckit.plan`                   |
| `/speckit.analyze` *(tùy chọn)*   | Kiểm tra tính nhất quán và liên kết giữa các phần tài liệu      | Sau `/speckit.tasks`, trước `/speckit.implement` |
| `/speckit.checklist` *(tùy chọn)* | Tạo checklist để đảm bảo yêu cầu đầy đủ, rõ ràng và nhất quán   | Sau khi chạy `/speckit.plan`                     |

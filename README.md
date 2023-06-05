# Tính giá trị chuỗi biểu thức

Tính giá trị chuỗi biểu thức chính là một trong những bài toán điển hình của việc ứng dụng cấu trúc dữ liệu Stack.

Ví dụ, ta có một chuỗi biểu thức `4 + 5 * 3` , với cách tư duy toán học của con người ta dễ dàng phân tích, các toán hạng, các phép toán phép nào phải làm trước, phép nào phải làm sau:

1. Đọc từ trái sang phải, thấy trong biểu thức có phép nhân và cộng. Độ ưu tiên phép cộng cao hơn so với độ ưu tiên của phép nhân nên tính `5 * 3` trước được kết quả là `15` .
2. Lúc này bài toán chuyển thành `4 + 15` được kết quả là `19` .

Để máy tính có thể hiểu và tính được thì ta cần chuyển biểu thức gốc, biểu thức có dạng trung tố (các toán tử bị kẹp giữa các toán hạng) thành biểu thức dạng hậu tố (các toán hạng đứng trước các toán tử) và tính toán. Để có thể hiểu được các dạng biểu thức, cũng như cách tính toán tương ứng với từng dạng biểu thức thì đọc thêm tại [đường dẫn](https://www.codingninjas.com/codestudio/library/infix-postfix-and-prefix-conversion) này.

Cho trước các chuỗi biểu thức lưu trong file text, tính và in lần lượt kết quả của từng biểu thức trên từng dòng khác nhau.

Giả sử chuỗi biểu thức nhập vào là có nghĩa và các toán hạng thuộc dạng số nguyên. 

Ví dụ:

| Input (file expression) | Output (terminal) |
| --- | --- |
| 4 + 5 * 3 | 4 + 5 * 3 = 19.0 |
| 10 / (3 + 2) - 7 * 2| 10 / (3 + 2) - 7 * 2 = -12.0|
---

Solution cho bài toán tính chuỗi giá trị biểu thức

1. Ứng dụng thuật toán ***Shunting Yard*** với các phát biểu bên dưới để chuyển biểu thức trung tố thành hậu tố:
    - Nếu là toán hạng: cho ra output.
    - Nếu là dấu mở ngoặc “(“: cho vào stack.
    - Nếu là dấu đóng ngoặc “)”: lấy các toán tử trong stack ra và cho vào output cho đến khi gặp dấu mở ngoặc “(“. (Dấu mở ngoặc cũng phải được đưa ra khỏi stack).
    - Nếu là toán tử:
        - Chừng nào ở đỉnh stack là toán tử và toán tử đó có độ ưu tiên lớn hơn hoặc bằng toán tử hiện tại thì lấy toán tử đó ra khỏi stack và cho ra output.
        - Đưa toán tử hiện tại vào stack.

   Sau khi duyệt hết biểu thức trung tố, nếu trong stack còn phần tử thì lấy các token trong đó ra và cho lần lượt vào output.

2. Ứng dụng các quy tắc sau để tính toán giá trị từ biểu thức hậu tố vừa tạo:
    - Đọc biểu thức từ trái sang phải.
    - Nếu gặp một toán hạng, đưa nó vào stack.
    - Nếu gặp một toán tử, lấy hai toán hạng cuối cùng từ stack và thực hiện phép toán tương ứng với toán tử đó. Sau đó, đưa kết quả của phép toán trở lại stack.
    - Lặp lại bước 2 và 3 cho đến khi duyệt qua toàn bộ biểu thức.
    - Khi đã duyệt hết biểu thức, kết quả cuối cùng sẽ nằm ở đỉnh của stack.

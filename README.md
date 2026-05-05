
<br>

# coderabbit이용해 pr시 자동 리뷰하는 기능 추가 예정-최종 

<br>

# AI Java MetaApple - Simple Calculator

Java Swing을 사용해 만든 간단한 GUI 계산기 프로젝트입니다.  
두 개의 숫자를 입력하고 `+`, `-`, `*`, `/` 버튼을 눌러 사칙연산 결과를 확인할 수 있습니다.

## 프로젝트 구조
```
text
ai-java-metaapple
├── src
│   └── test
│       ├── SimpleCalculator.java
│       └── calc_icon.png
├── .gitignore
└── ai-java.iml
```
## 개발 환경

- Java SDK: 17
- GUI 라이브러리: Java Swing
- 실행 클래스: `test.SimpleCalculator`

## 주요 기능

- 숫자 2개 입력
- 덧셈, 뺄셈, 곱셈, 나눗셈 계산
- 0으로 나누기 예외 처리
- 숫자가 아닌 값 입력 시 경고 메시지 표시
- `JOptionPane`을 이용한 결과 팝업 출력
- 계산기 아이콘 이미지 표시

## 실행 방법

프로젝트 루트에서 다음 명령어를 실행합니다.
```
bash
javac src/test/SimpleCalculator.java
java -cp src test.SimpleCalculator
```
또는 IntelliJ IDEA에서 `SimpleCalculator.java` 파일의 `main` 메서드를 실행하면 됩니다.

## 코드 설명

아래는 핵심 코드에 주석을 추가한 예시입니다.
```
java
package test;

// Java Swing GUI 컴포넌트 사용
import javax.swing.*;

// 레이아웃, 색상, 폰트 등 AWT 기능 사용
import java.awt.*;

// 버튼 클릭 이벤트 처리를 위한 클래스
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculator extends JFrame {
// 첫 번째 숫자를 입력받는 텍스트 필드
private JTextField num1Field;

    // 두 번째 숫자를 입력받는 텍스트 필드
    private JTextField num2Field;

    public SimpleCalculator() {
        // 창 제목 설정
        setTitle("Colorful Calculator");

        // 창 크기 설정
        setSize(450, 350);

        // 창을 닫으면 프로그램 종료
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 전체 배경색 설정
        getContentPane().setBackground(new Color(240, 248, 255));

        // 전체 레이아웃 설정
        setLayout(new BorderLayout(10, 10));

        // 상단 영역 생성
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(100, 149, 237));

        // 제목 라벨 생성
        JLabel titleLabel = new JLabel("My Calculator", JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));

        // 계산기 아이콘 이미지 추가
        try {
            ImageIcon icon = new ImageIcon("src/test/calc_icon.png");
            Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            titleLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            // 이미지 파일이 없을 경우 아이콘 없이 실행
            System.out.println("Image not found, skipping icon.");
        }

        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        // 중앙 입력 영역 생성
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        centerPanel.setBackground(new Color(240, 248, 255));

        JLabel lbl1 = new JLabel("Number 1:");
        lbl1.setFont(new Font("SansSerif", Font.BOLD, 14));
        num1Field = new JTextField();
        num1Field.setBackground(new Color(255, 255, 224));

        JLabel lbl2 = new JLabel("Number 2:");
        lbl2.setFont(new Font("SansSerif", Font.BOLD, 14));
        num2Field = new JTextField();
        num2Field.setBackground(new Color(255, 255, 224));

        centerPanel.add(lbl1);
        centerPanel.add(num1Field);
        centerPanel.add(lbl2);
        centerPanel.add(num2Field);
        add(centerPanel, BorderLayout.CENTER);

        // 하단 버튼 영역 생성
        JPanel bottomPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        bottomPanel.setBackground(new Color(240, 248, 255));

        String[] ops = {"+", "-", "*", "/"};

        Color[] btnColors = {
            new Color(255, 182, 193),
            new Color(173, 216, 230),
            new Color(144, 238, 144),
            new Color(255, 218, 185)
        };

        // 연산자 버튼 생성
        for (int i = 0; i < ops.length; i++) {
            JButton btn = new JButton(ops[i]);
            btn.setBackground(btnColors[i]);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            btn.setFont(new Font("SansSerif", Font.BOLD, 18));

            // 버튼 클릭 시 계산 이벤트 실행
            btn.addActionListener(new CalcActionListener());

            bottomPanel.add(btn);
        }

        add(bottomPanel, BorderLayout.SOUTH);

        // 창을 화면 중앙에 배치
        setLocationRelativeTo(null);

        // 창 표시
        setVisible(true);
    }

    private class CalcActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // 입력값을 숫자로 변환
                double n1 = Double.parseDouble(num1Field.getText());
                double n2 = Double.parseDouble(num2Field.getText());

                double result = 0;

                // 클릭된 버튼의 연산자 가져오기
                String op = e.getActionCommand();

                switch (op) {
                    case "+":
                        result = n1 + n2;
                        break;
                    case "-":
                        result = n1 - n2;
                        break;
                    case "*":
                        result = n1 * n2;
                        break;
                    case "/":
                        // 0으로 나누기 방지
                        if (n2 == 0) {
                            showResult(
                                "Error: Cannot divide by zero!",
                                "Math Error",
                                JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }
                        result = n1 / n2;
                        break;
                }

                // 계산 결과 표시
                showResult(
                    "The result is: " + result,
                    "Calculation Result",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } catch (NumberFormatException ex) {
                // 숫자가 아닌 값이 입력된 경우
                showResult(
                    "Error: Please enter valid numbers!",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        }
    }

    private void showResult(String message, String title, int messageType) {
        // JOptionPane으로 결과 또는 오류 메시지 표시
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    public static void main(String[] args) {
        // Swing GUI를 이벤트 디스패치 스레드에서 실행
        SwingUtilities.invokeLater(() -> new SimpleCalculator());
    }
}
```
## 동작 흐름

1. 사용자가 프로그램을 실행합니다.
2. 계산기 창이 표시됩니다.
3. 사용자가 두 숫자를 입력합니다.
4. 사용자가 연산 버튼을 클릭합니다.
5. 입력값을 숫자로 변환합니다.
6. 선택한 연산자에 따라 계산합니다.
7. 결과 또는 오류 메시지를 팝업으로 표시합니다.

## 시퀀스 다이어그램
```
mermaid
sequenceDiagram
actor User as 사용자
participant UI as SimpleCalculator 화면
participant Listener as CalcActionListener
participant Parser as 숫자 변환
participant Calculator as 계산 로직
participant Dialog as JOptionPane

    User->>UI: 숫자 2개 입력
    User->>UI: 연산 버튼 클릭
    UI->>Listener: actionPerformed(event) 호출
    Listener->>Parser: 입력값을 double로 변환

    alt 숫자 변환 성공
        Parser-->>Listener: n1, n2 반환
        Listener->>Calculator: 연산자에 따라 계산 수행

        alt 0으로 나누기
            Calculator-->>Listener: 수학 오류
            Listener->>Dialog: 오류 메시지 표시
            Dialog-->>User: "Cannot divide by zero!"
        else 정상 계산
            Calculator-->>Listener: 계산 결과 반환
            Listener->>Dialog: 결과 메시지 표시
            Dialog-->>User: 계산 결과 확인
        end

    else 숫자 변환 실패
        Parser-->>Listener: NumberFormatException 발생
        Listener->>Dialog: 입력 오류 메시지 표시
        Dialog-->>User: "Please enter valid numbers!"
    end
```
## 예외 처리

| 상황 | 처리 방식 |
|---|---|
| 숫자가 아닌 값 입력 | `NumberFormatException` 처리 후 경고 메시지 표시 |
| 0으로 나누기 | 계산 전 `n2 == 0` 검사 후 오류 메시지 표시 |
| 이미지 파일 없음 | `try-catch` 처리 후 아이콘 없이 실행 |

## 참고 사이트

1. Oracle Java SE 17 `JFrame` API 문서  
   https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/JFrame.html

2. Oracle Java SE 17 `JOptionPane` API 문서  
   https://docs.oracle.com/javase/jp/17/docs/api/java.desktop/javax/swing/JOptionPane.html

3. Mermaid Sequence Diagram 공식 문서  
   https://mermaid.ai/open-source/syntax/sequenceDiagram.html

## 라이선스

학습 및 실습용 프로젝트입니다.
```


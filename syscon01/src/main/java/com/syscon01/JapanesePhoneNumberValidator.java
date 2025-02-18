package com.syscon01;

public class JapanesePhoneNumberValidator {

    /**
     * 与えられた電話番号文字列が有効な日本の電話番号形式かチェックする。
     * <p>
     * 有効な形式は以下のいずれか：
     * <ul>
     *   <li>数字のみ（10桁または11桁、最初は0）</li>
     *   <li>ハイフン形式："0xxx-xxxx-xxxx" のように、2か所のハイフンで区切られた3グループ</li>
     *   <li>括弧形式："0xxx(xxxx)xxxx" のように、括弧で市内局番（2番目の数字列）を囲み、ハイフンは使用しない</li>
     * </ul>
     *
     * @param phoneNumber チェック対象の電話番号文字列
     * @return 有効な形式の場合は true、そうでなければ false
     */
    public boolean isValid(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }

        // 数字以外に使える記号はハイフンと括弧のみなので、その他の文字があればNG
        if (!phoneNumber.matches("[0-9\\-()]+")) {
            return false;
        }

        // 電話番号中の数字のみを抽出し、全体が10桁または11桁であること、かつ先頭が'0'であることをチェック
        String digits = phoneNumber.replaceAll("[^0-9]", "");
        if (!(digits.length() == 10 || digits.length() == 11)) {
            return false;
        }
        if (!digits.startsWith("0")) {
            return false;
        }

        // 括弧とハイフンの使用状況で分岐
        if (phoneNumber.contains("(") || phoneNumber.contains(")")) {
            // 括弧形式の場合、ハイフンが混在してはいけない
            if (phoneNumber.contains("-")) {
                return false;
            }
            // 括弧形式は、エリアコード（市外局番）の後に括弧で囲まれた市内局番、その後加入者番号が続く形式とする
            // 例: "03(1234)5678"
            if (!phoneNumber.matches("^0\\d+\\(\\d+\\)\\d+$")) {
                return false;
            }
        } else if (phoneNumber.contains("-")) {
            // ハイフン形式の場合、ハイフンは必ず2つのみ使われる（3グループに分かれる）
            int hyphenCount = phoneNumber.length() - phoneNumber.replace("-", "").length();
            if (hyphenCount != 2) {
                return false;
            }
            // ハイフン形式は、"0xxxx-xxxx-xxxx" のように、各グループは数字のみである必要がある
            if (!phoneNumber.matches("^0\\d+-\\d+-\\d+$")) {
                return false;
            }
        }
        // 記号が含まれない場合（数字のみ）は既に桁数等をチェックしているのでOK

        return true;
    }

    // 動作確認用のメインメソッド（必要に応じてテストしてください）
    public static void main(String[] args) {
        JapanesePhoneNumberValidator validator = new JapanesePhoneNumberValidator();

        String[] testNumbers = {
            "0312345678",       // 数字のみ（10桁）
            "08012345678",       // 数字のみ（11桁）
            "03-1234-5678",      // ハイフン形式（10桁）
            "080-1234-5678",     // ハイフン形式（11桁）
            "03(1234)5678",      // 括弧形式（10桁）
            "080(1234)5678",     // 括弧形式（11桁）
            "03(123)-45678",     // 括弧形式だがハイフンが混在 → NG
            "03-12345678",       // ハイフンが1つのみ → NG
            "03(12)34-5678",     // 括弧とハイフン混在 → NG
            "123-4567-8901",     // 先頭が0でない → NG
            "03-1234-567",       // 数字の合計桁数が不正 → NG
            "03(12345)6789"      // 括弧内の桁数に制限は設けず、全体桁数が10または11ならOK（必要に応じて調整可）
        };

        for (String number : testNumbers) {
            System.out.println(number + " -> " + validator.isValid(number));
        }
    }
}

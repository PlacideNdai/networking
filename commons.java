package Lab_5;

public class commons {

    public static void main(String[] args) {
        new commons();
    }

    // encrpting
    public commons() {
        // testing only for encrption
        String testingString = "This is a testing encription string";
        encription(testingString);
    }

    // encripting the string before sending to the client
    public String encription(String str) {
        byte[] inbyte = str.getBytes();
        StringBuilder resultsByte = new StringBuilder();

        for (int i = 0; i < inbyte.length; i++) {
            resultsByte.append((byte) (inbyte[i] + Byte.parseByte("52")));
        }
        // returning
        System.err.println(resultsByte);
        return resultsByte.toString();
    }

    // decripting the string for the client
    public String decrypting(String str) {

        return str;
    }
}

import com.google.protobuf.InvalidProtocolBufferException;
import de.thi.inf.sesa.simplegrpc.HelloRequest;

public class Main {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        HelloRequest request = HelloRequest.newBuilder()
                .setFirstName("Erna")
                .setLastName("Musterfrau")
                .build();

        byte[] requestBytes = request.toByteArray();
        System.out.println("Request bytes: " + requestBytes.length); // {"firstName": "Erna", "lastName": "Musterfrau"} -> 32 bytes

        // ---

        HelloRequest request2 = HelloRequest.parseFrom(requestBytes);
        System.out.println(request2.getFirstName() + " " + request2.getLastName());
    }
}
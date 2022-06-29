package zad1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Futil extends SimpleFileVisitor {
    private static Charset decode  = Charset.forName("Cp1250");
    private static Charset code = Charset.forName("UTF-8");
    private static FileChannel fileChannel;

    public static void processDir(String dirName, String resultFileName) {
        try {
            fileChannel = FileChannel.open(Paths.get(resultFileName), StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.walkFileTree(Paths.get(dirName), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    FileChannel readChannel = FileChannel.open(file, StandardOpenOption.READ);
                    ByteBuffer byteBuffer = ByteBuffer.allocate((int) readChannel.size());
                    readChannel.read(byteBuffer);
                    byteBuffer.flip();

                    CharBuffer charBuffer = decode.decode(byteBuffer);
                    byteBuffer = code.encode(charBuffer);

                    fileChannel.write(byteBuffer);
                    readChannel.close();
                    return FileVisitResult.CONTINUE;
                }
            });
            fileChannel.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
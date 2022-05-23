import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void saveGames(String path, GameProgress player) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream obj = new ObjectOutputStream(fos)) {
            obj.writeObject(player);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String pathArchive, List<String> path) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(pathArchive))) {
            int count = 0;
            for (String p : path) {
                count++;
                FileInputStream fis = new FileInputStream(p);
                ZipEntry entry = new ZipEntry("zip" + count + ".dat");
                zos.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zos.write(buffer);
                zos.closeEntry();
                fis.close();
                if (new File(p).delete()) {
                    System.out.println("Файлы не лежащие в архиве удалены");
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public static void main(String[] args) {

        List<GameProgress> player = new ArrayList<>();
        List<String> path = new ArrayList<>();
        int count = 0;

        GameProgress player1 = new GameProgress(100, 3, 80, 55);
        GameProgress player2 = new GameProgress(90, 5, 70, 50);
        GameProgress player3 = new GameProgress(77, 3, 90, 60);

        player.add(player1);
        player.add(player2);
        player.add(player3);

        for (GameProgress gm : player) {
            count++;
            String p = "C://Games//saveGames//save" + count + ".dat";
            saveGames(p, gm);
            path.add(p);
        }
        zipFiles("C://Games//saveGames//zip.zip", path);
    }
}

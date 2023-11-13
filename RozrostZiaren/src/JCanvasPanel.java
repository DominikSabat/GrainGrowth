import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class JCanvasPanel extends JPanel{

    DataManager dm;

    public JCanvasPanel(DataManager dm) {
        this.dm = dm;
    }


    @Override
        public void paint(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(dm.img, ((1680-600-dm.width)/2), ((1050-dm.height)/2), this);
        }

    public void exportImage() {

        String imageName = "export.bmp";

        BufferedImage image = new  BufferedImage(dm.width, dm.height,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();


        renderSeed(graphics);

        graphics.dispose();

        try {
            System.out.println("Wyeksportowano obraz o nazwie: '"+imageName+ "' do katalogu z projektem");
            FileOutputStream out = new FileOutputStream(imageName);
            ImageIO.write(image, "bmp", out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void renderSeed(Graphics2D g2) { g2.drawImage(dm.img, 0, 0, this); }

    @Override
    public void repaint() {
        super.repaint();
    }

}
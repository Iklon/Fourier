package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.FileChooser;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private LineChart<Number, Number> linechart;
    @FXML
    private TextField ftop;
    @FXML
    private TextField fbot;

    private byte[] content;


    @FXML
    private void load() {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("D:\\Users\\Iklon\\Desktop\\Fuhrer"));
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            Path path = Paths.get(file.getPath().toString());
            try {
                content = Files.readAllBytes(path);
            } catch (IOException exc) {exc.printStackTrace();}
            draw();
        }
    }

    @FXML
    private void refresh() {
        draw();
    }

    private void draw() {
        int top = Integer.parseInt(ftop.getText());
        int bot = Integer.parseInt(fbot.getText());
        XYChart.Series series = new XYChart.Series();
        for (int a=bot; a<=top; a++) {
            series.getData().add(new XYChart.Data(a, Fuhrer(a, content.length, content)));
        }
        linechart.getData().add(series);
    }

    private float Fuhrer(int frequency, int samples, byte[] content) {
        float sum=0, x=0, y=0;
        for(int a=0; a<samples; a++) {
            x = byteToUnsigned(content[a]) * (float)Math.cos((2*3.14*a*frequency)/(samples));
            y = byteToUnsigned(content[a]) * (float)Math.sin((2*3.14*a*frequency)/(samples));
            sum += x+y;
        }
        return sum/samples;
    }

    private static int byteToUnsigned(byte a) {
        return a & 0xFF;
    }
}

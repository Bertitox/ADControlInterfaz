package org.example.adcontrol;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.*;

public class ControladorAjustes {

    @FXML
    private MenuButton MenuButtonIdiomas;

    @FXML
    private CheckBox CheckBoxSonido;

    @FXML
    private ImageView imageViewSonido;

    private Image imagenSonidoEncendido;
    private Image imagenSonidoApagado;

    @FXML
    private Label labelVolumen;

    @FXML
    private Slider sliderVolumen;

    @FXML
    ColorPicker colorPicker;

    InfoInit infoInit = InfoInit.getInstance();


    @FXML
    public void initialize() throws IOException {
        cargarDatos();
        imagenSonidoApagado = new Image(getClass().getResourceAsStream("/org/example/adcontrol/Imagenes/volumenapagado.png"));
        imagenSonidoEncendido = new Image(getClass().getResourceAsStream("/org/example/adcontrol/Imagenes/volumenencendido.png"));

        //FUNCIONA
        for (MenuItem item : MenuButtonIdiomas.getItems()) {
            item.setOnAction(e -> {
                MenuButtonIdiomas.setText(item.getText());
                infoInit.setIdiomaLeido(MenuButtonIdiomas.getText());
            });
        }

        //FUNCIONA
        CheckBoxSonido.setOnAction(e -> {
            if (CheckBoxSonido.isSelected()) {
                imageViewSonido.setImage(imagenSonidoEncendido);
                infoInit.setMuteLeido(false);
                sliderVolumen.setDisable(false);
                labelVolumen.setDisable(false);
            } else {
                imageViewSonido.setImage(imagenSonidoApagado);
                infoInit.setMuteLeido(true);
                sliderVolumen.setDisable(true);
                labelVolumen.setDisable(true);
            }
        });
        imageViewSonido.setImage(CheckBoxSonido.isSelected() ? imagenSonidoEncendido : imagenSonidoApagado);
        //FUNCIONA
        sliderVolumen.setOnMouseReleased(e -> {
            Double vol = sliderVolumen.getValue() * 0.01;
            infoInit.setVolumenLeido(vol);
        });


        colorPicker.setOnAction(e -> {
            Color color = colorPicker.getValue();
            String hex = String.format("#%02X%02X%02X",
                    (int)(color.getRed() * 255),
                    (int)(color.getGreen() * 255),
                    (int)(color.getBlue() * 255)
            );
            infoInit.setTemaLeido(hex);
        });
    }

    public void cargarDatos(){
        MenuButtonIdiomas.setText(infoInit.getIdiomaLeido());

        if(infoInit.getMuteLeido()){
            CheckBoxSonido.setSelected(false);
        }else{
            CheckBoxSonido.setSelected(true);
        }

        sliderVolumen.setValue(infoInit.getVolumenLeido() * 100.0);

        Color color = Color.web(infoInit.getTemaLeido());
        colorPicker.setValue(color);
    }
}

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
    private Slider sliderVolumen;

    @FXML
    ColorPicker colorPicker;



    @FXML
    public void initialize() throws IOException {
        cargarDatos();
        imagenSonidoApagado = new Image(getClass().getResourceAsStream("/org/example/adcontrol/Imagenes/volumenapagado.png"));
        imagenSonidoEncendido = new Image(getClass().getResourceAsStream("/org/example/adcontrol/Imagenes/volumenencendido.png"));

        for (MenuItem item : MenuButtonIdiomas.getItems()) {
            item.setOnAction(e -> MenuButtonIdiomas.setText(item.getText()));
        }

        CheckBoxSonido.setOnAction(e -> {
            if (CheckBoxSonido.isSelected()) {
                imageViewSonido.setImage(imagenSonidoEncendido);
            } else {
                imageViewSonido.setImage(imagenSonidoApagado);
            }
        });

        imageViewSonido.setImage(CheckBoxSonido.isSelected() ? imagenSonidoEncendido : imagenSonidoApagado);
    }

    public void cargarDatos(){
        InfoInit infoInit = InfoInit.getInstance();
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

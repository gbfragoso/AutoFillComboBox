package com.biblioteca2.fxcontrols;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Extension of JavaFX combobox with autofill property, based on JulianG approach with some enhacements.
 * @author Gustavo Fragoso
 */

public class AutoFillComboBox extends ComboBox {
    
    private ObservableList<String> items;
    private final TextField editor;

    public AutoFillComboBox() {
        editor = getEditor();
    }
    
    public AutoFillComboBox(ObservableList<String> items){
        super(items);
        editor = getEditor();
        setAutoFill(true);
        setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                hide();
            }
        });
        setOnKeyReleased(createHandler());
    }
    
    public final void setAutoFill(boolean b){
        if(b){
            setEditable(true);
            items = getItems();
        }else{
            setEditable(false);
        }
    } 
    
    private EventHandler createHandler(){
        EventHandler e = new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                // Avoid excessive functions call
                KeyCode code = event.getCode();
                String text = editor.getText();
                int length = text.length();
                
                if(code == KeyCode.UP) {
                    editor.positionCaret(length);
                    return;
                } else if(code == KeyCode.DOWN) {
                    if(!isShowing()) {
                        show();
                    }
                    editor.positionCaret(length);
                    return;
                } else if(code == KeyCode.BACK_SPACE) {
                    editor.positionCaret(length-1);
                    getSelectionModel().clearSelection();
                } else if(code == KeyCode.DELETE) {
                    editor.positionCaret(length-1);
                    getSelectionModel().clearSelection();
                }

                if (code == KeyCode.RIGHT || code == KeyCode.LEFT
                        || event.isControlDown() || code == KeyCode.HOME
                        || code == KeyCode.END || code == KeyCode.TAB) {
                    return;
                }

                String lower = text.toLowerCase();                
                // Search
                Optional<String> string = items.stream().filter(p -> p.toLowerCase().startsWith(lower)).findFirst();
                // If find someone
                if(string.isPresent()){
                    int pos = items.indexOf(string.get());
                    // Avoiding bug: Caret at first letter doesn't move right.
                    if(code == KeyCode.ENTER){
                        ListView lv = ((ComboBoxListViewSkin) getSkin()).getListView();
                        lv.scrollTo(pos);
                        lv.getSelectionModel().clearAndSelect(pos);
                        editor.positionCaret(string.get().length());  
                    }else{
                        show();
                        ListView lv = ((ComboBoxListViewSkin) getSkin()).getListView();
                        lv.scrollTo(pos);
                        lv.getSelectionModel().clearAndSelect(pos);
                        editor.setText(text);
                        editor.positionCaret(lower.length());
                    }
                }                         
            }
        }; 
        return e;
    }
}
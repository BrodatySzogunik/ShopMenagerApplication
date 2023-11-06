package frames.Helper;

import Structures.DataBase.Products.Category.Category;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.List;

public class CategoryComboBoxModel implements ComboBoxModel {

    private List<Category> categoryList;
    private String selectedValue;

    public CategoryComboBoxModel(List<Category> data){
        this.categoryList = data;
    }
    @Override
    public void setSelectedItem(Object anItem) {
        this.selectedValue =  (String) anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selectedValue;
    }

    @Override
    public int getSize() {
        return categoryList.size();
    }

    @Override
    public Object getElementAt(int index) {
        if (index >= 0 && index < categoryList.size()) {
            return categoryList.get(index).getCategoryName();
        }
        return null;
    }

    public Object getElementIdAt(int index){
        if(index >= 0 && index < categoryList.size()){
            return categoryList.get(index).getId();
        }
        return null;
    }

    @Override
    public void addListDataListener(ListDataListener l) {

    }

    @Override
    public void removeListDataListener(ListDataListener l) {

    }
}

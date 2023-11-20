package frames.Helper;

import Structures.Intercars.InvoicesList.nag;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.List;

public class InvoiceComboBoxModel implements ComboBoxModel {

    private List<nag> invoiceList;
    private String selectedValue;
    private nag selectedInvoice;


    public InvoiceComboBoxModel(List<nag> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public void updateComboBox(List<nag> invoiceList){
        this.invoiceList = invoiceList;
    }

    @Override
    public void setSelectedItem(Object anItem) {

        this.selectedInvoice = (nag) anItem;
        this.selectedValue =  selectedInvoice.toString();
    }

    @Override
    public Object getSelectedItem() {
        return selectedInvoice;
    }

    @Override
    public int getSize() {
        return invoiceList.size();
    }

    @Override
    public Object getElementAt(int index) {
        if (index >= 0 && index < invoiceList.size()) {
            return invoiceList.get(index);
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

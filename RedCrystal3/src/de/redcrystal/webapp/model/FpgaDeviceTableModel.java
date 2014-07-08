package de.redcrystal.webapp.model;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import de.redcrystal.webapp.model.fpga.FpgaDevice;

/**
 * the fpga device table model
 * @author Tran
 *
 */
public class FpgaDeviceTableModel extends ListDataModel<FpgaDevice> implements SelectableDataModel<FpgaDevice>, Serializable{

    /**
     * generated serial version id
     */
    private static final long serialVersionUID = 5829167673229632594L;

    

    /**
     * default constructor
     * 
     * @param devices
     *            the data table rows
     */
    public FpgaDeviceTableModel(List<FpgaDevice> devices) {
        super(devices);
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public FpgaDevice getRowData(String key) {
        List<FpgaDevice> devices = (List<FpgaDevice>) getWrappedData();
        for (FpgaDevice device : devices) {
            if (String.valueOf(device.getAddress()).equals(key)) {
                return device;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getRowKey(FpgaDevice object) {
        return String.valueOf(object.getAddress());
    }

}

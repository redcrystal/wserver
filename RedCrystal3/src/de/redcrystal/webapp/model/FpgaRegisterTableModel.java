/**
 * 
 */
package de.redcrystal.webapp.model;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import de.redcrystal.webapp.model.fpga.FpgaRegister;

/**
 * the routing table model
 * 
 * @author mngo
 * 
 */
public class FpgaRegisterTableModel extends ListDataModel<FpgaRegister> implements SelectableDataModel<FpgaRegister>, Serializable {

    /**
     * generated serial version id
     */
    private static final long serialVersionUID = -7314326415977268011L;

    /**
     * default constructor
     */
    public FpgaRegisterTableModel() {
    }

    /**
     * default constructor
     * 
     * @param registers
     *            the data table row
     */
    public FpgaRegisterTableModel(List<FpgaRegister> registers) {
        super(registers);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public FpgaRegister getRowData(String rowKey) {
        List<FpgaRegister> fpgaRegisters = (List<FpgaRegister>) getWrappedData();
        for (FpgaRegister fpgaRegister : fpgaRegisters) {
            if (String.valueOf(fpgaRegister.getIndex()).equals(rowKey)) {
                return fpgaRegister;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getRowKey(FpgaRegister aRouting) {
        return String.valueOf(aRouting.getIndex());
    }
    
    

}

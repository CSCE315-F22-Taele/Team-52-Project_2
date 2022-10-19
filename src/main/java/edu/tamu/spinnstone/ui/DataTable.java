package edu.tamu.spinnstone.ui;

import rx.subjects.PublishSubject;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;

public class DataTable extends AbstractTableModel implements TableModelListener {
    private ArrayList<ArrayList<String>> data;
    private ArrayList<String> columnNames;
    private PublishSubject<TableModelEvent> stream;
    private ArrayList<Integer> editableColumns;
    public boolean suppressEvents;

    /**
     * @param rawData
     * @param rawColumnNames
     * @param _editableColumns
     */
    public DataTable(String[][] rawData, String[] rawColumnNames, Integer[] _editableColumns) {
        columnNames = new ArrayList<>();
        columnNames.addAll(Arrays.asList(rawColumnNames));

        data = new ArrayList<ArrayList<String>>();

        editableColumns = new ArrayList<Integer>();
        editableColumns.addAll(Arrays.asList(_editableColumns));

        for (String[] row : rawData) {
            ArrayList<String> row_array = new ArrayList<>();
            row_array.addAll(Arrays.asList(row));
            data.add(row_array);
        }

        suppressEvents = false;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
//        return editableColumns.contains(col);
        return true;
    }

    public DataTable(String[][] rawData, String[] rawColumnNames, Integer[] editableColumns, PublishSubject<TableModelEvent> _stream) {
        this(rawData, rawColumnNames, editableColumns);
        stream = _stream;
    }

    @Override
    public String getColumnName(int index) {
        return columnNames.get(index);
    }

    public ArrayList<ArrayList<String>> getData() {
        return data;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (suppressEvents == true) {
            return;
        }
        if (stream != null) {
            stream.onNext(e);
        }
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        if (data.isEmpty()) {
            return 0;
        }
        return data.get(0).size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            return data.get(rowIndex).get(columnIndex);
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data.get(rowIndex).set(columnIndex, aValue.toString());
        tableChanged(new TableModelEvent(this, rowIndex, rowIndex, columnIndex));
    }
}

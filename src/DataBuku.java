import Perpustakaan.Perpustakaan;
import Perpustakaan.PerpustakaanApp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;


public class DataBuku extends JFrame {
    private DefaultListModel<String> listModel;
    private JList<String> dataJList;
    private JTextField judulTextField; 
    private JTextField isiTextArea;
    
    private JRadioButton sedangDiPinjamRadioButton;
    private JRadioButton sudahDiKembalikanRadioButton;
    private ButtonGroup statusButtonGroup;

    private PerpustakaanApp catatanApp;

    public DataBuku() {
        catatanApp = new PerpustakaanApp();

        setTitle("Perpustakaan App");
        //setSize(280, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600 , 400);

        setLocationRelativeTo(null);

        sedangDiPinjamRadioButton = new JRadioButton("Belum diKembalikan");
        sudahDiKembalikanRadioButton = new JRadioButton("Sudah diKembalikan");
        statusButtonGroup = new ButtonGroup();

        statusButtonGroup.add(sedangDiPinjamRadioButton);
        statusButtonGroup.add(sudahDiKembalikanRadioButton);

        try {
            Image icon = ImageIO.read(new File("C:\\Users\\Lenovo\\Documents\\PerpustakaanApp\\Perpustkaan.Zen\\src\\pngegg.png"));
            setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setResizable(false);

        getContentPane().setBackground(new Color(0, 0, 0)); 


        listModel = new DefaultListModel<>();
        dataJList = new JList<>(listModel);
        dataJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dataJList.addListSelectionListener(e -> tampilkanCatatan());

        judulTextField = new JTextField();
        isiTextArea = new JTextField();
        
        
        JButton tambahButton = new JButton("Tambah Data");
        tambahButton.addActionListener(e -> tambahData());

        JButton hapusButton = new JButton("Hapus Data");
        hapusButton.addActionListener(e -> hapusData());

        JButton resetButton = new JButton("Reset data");
        resetButton.addActionListener(e -> resetData());

        setLayout(new BorderLayout());

        JLabel juduLabel = new JLabel("  Judul: ");
        juduLabel.setForeground(Color.WHITE);

        JLabel isiLabel = new JLabel("  Nama: ");
        isiLabel.setForeground(Color.WHITE);

        JLabel statusLabel = new JLabel("   Status Pinjam: ");
        statusLabel.setForeground(Color.WHITE);

        JPanel panelKiri = new JPanel();
        //panelKiri.setLayout(new GridLayout(9, 1));
        panelKiri.setLayout(new BoxLayout(panelKiri,BoxLayout.Y_AXIS));
        
        panelKiri.setLayout(new GridLayout(9, 1));
        panelKiri.add(juduLabel); 
        panelKiri.add(judulTextField);
        panelKiri.add(isiLabel);
        panelKiri.add(isiTextArea);
        panelKiri.add(statusLabel);
        panelKiri.add(sedangDiPinjamRadioButton);
        panelKiri.add(sudahDiKembalikanRadioButton);
        panelKiri.add(tambahButton);
        panelKiri.add(resetButton, BorderLayout.PAGE_END);
        panelKiri.setBackground(new Color(0, 0, 0));
        //code panel kiri

        JPanel panelKanan = new JPanel();
    
        panelKanan.setLayout(new BoxLayout(panelKanan, BoxLayout.Y_AXIS));
        panelKanan.add(new JScrollPane(dataJList), BorderLayout.CENTER);
        panelKanan.add(hapusButton, BorderLayout.CENTER);
        panelKanan.setBackground(new Color(0, 0, 0));
        //code panel kanan


        add(panelKiri, BorderLayout.WEST);
        add(panelKanan, BorderLayout.CENTER);
    }

    private void resetData() 
    {
        // Clear the title and content fields
        judulTextField.setText("");
        isiTextArea.setText("");
    }
    
    private void tampilkanCatatan()
    {
        int index = dataJList.getSelectedIndex();
        if (index != -1)
        {
            Perpustakaan catatan = catatanApp.getCatatanList().get(index);
            judulTextField.setText(catatan.getJudul());
            isiTextArea.setText(catatan.getIsi());
            
        }
    }

    private void tambahData() {
        String judul = judulTextField.getText();
        String isi = isiTextArea.getText();
        String status;
    
        if (sedangDiPinjamRadioButton.isSelected()) {
            status = "Sedang di Pinjam";
        } else if (sudahDiKembalikanRadioButton.isSelected()) {
            status = "Sudah di Kembalikan";
        } else {
            JOptionPane.showMessageDialog(this, "Pilih status pinjam terlebih dahulu", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Hentikan eksekusi jika tidak ada status yang dipilih
        }
        
        if(!judul.isEmpty() && !isi.isEmpty() && !status.isEmpty())
        {
            Perpustakaan dataBaru = new Perpustakaan(judul, isi, status);
            catatanApp.tambahCatatan(dataBaru);
            refreshList();
            resetForm();
            simpanDataKeFile();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Laman tidak boleh kosong", "Error", JOptionPane.ERROR_MESSAGE);
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("data_buku.dat");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(catatanApp.getCatatanList());
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan data ke file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusData()
    {
        int index = dataJList.getSelectedIndex();
        if(index != -1)
        {
            catatanApp.hapusCatatan(index);
            refreshList();
            resetForm();
            simpanDataKeFile();
        } 
    }
    
        private void simpanDataKeFile(){

            try {
            FileOutputStream fileOutputStream = new FileOutputStream("data_buku.dat");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(catatanApp.getCatatanList());
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan data ke file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void refreshList()
    {
        listModel.removeAllElements();
        for(Perpustakaan catatan : catatanApp.getCatatanList())
        {
            listModel.addElement(catatan.getJudul());
        }
    }

    private void resetForm()
    {
        judulTextField.setText("");
        isiTextArea.setText("");
        
        dataJList.clearSelection();
        sedangDiPinjamRadioButton.setSelected(false);
        sudahDiKembalikanRadioButton.setSelected(false);
    }

    
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->{
            DataBuku aplikasi = new DataBuku();
            aplikasi.setVisible(true);
        });
    }

}


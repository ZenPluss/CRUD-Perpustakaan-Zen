package Perpustakaan;

import java.io.Serializable;

public class Perpustakaan implements Serializable
{
    private String judul;
    private String isi;
    private String status;
   

    public Perpustakaan(String judul, String isi, String status)
    {
        this.judul = judul;
        this.isi = isi;
        this.status = status;
       
    }

    public String getJudul()
    {
        return judul;
    }

    public void setJudul(String judul)
    {  
        this.judul = judul;
    }

    public String getIsi()
    {
        return isi;
    }

    public void setIsi(String isi)
    {
        this.isi = isi;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return judul; 
    }
}
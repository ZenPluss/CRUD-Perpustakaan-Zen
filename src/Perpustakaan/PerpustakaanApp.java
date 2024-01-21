package Perpustakaan;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class PerpustakaanApp
{
    private List<Perpustakaan> catatanList = new ArrayList<>();

    public void tambahCatatan(Perpustakaan catatan)
    {
        catatanList.add(catatan);
    }

    public List<Perpustakaan> getCatatanList()
    {
        return catatanList;
    }

    public void hapusCatatan(int index)
    {
        catatanList.remove(index);
    }
}

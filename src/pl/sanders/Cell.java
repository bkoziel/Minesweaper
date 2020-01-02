package pl.sanders;

public class Cell {
protected short value; // 1-8 is Number of neighbours , 9 >=  is Bomb
protected boolean isFlagged;
protected boolean isDiscovered;

public Cell (){
    value = 0;
    isDiscovered = false;
    isFlagged = false;
}

}

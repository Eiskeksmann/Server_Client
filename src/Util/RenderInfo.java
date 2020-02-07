package Util;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RenderInfo<L extends Location, B extends BufferedImage> {

    private ArrayList<L> l;
    private ArrayList<B> b;

    public RenderInfo(){

        l = new ArrayList<>();
        b = new ArrayList<>();
    }

    public int getSize(){
        if(l.size() == b.size()){
            return l.size();
        }
        return -1;
    }
    public ArrayList<L> getL(){ return l; };
    public ArrayList<B> getB(){ return b; };
    public L getLAt(int i){ return l.get(i); }
    public B getBAt(int i){ return b.get(i); }

    public void add(L loc, B img){

        l.add(loc);
        b.add(img);
    }
    public void resetRenderInfo(){
        l.clear();
        b.clear();
    }
}

/*
    Arquivo versão 2.0
    Contempla o uso de buscas e árvores mínimas geradoras
*/

package grafos;

public class Arco implements Comparable<Arco>{
    
    private Vertice destino;
    private Vertice origem;
    private double peso;
    
    public Arco(Vertice origem, Vertice destino, double peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    public Vertice getOrigem() {
        return origem;
    }

    public Vertice getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }
    
    private boolean seguro;

    public boolean isSeguro() {
        return seguro;
    }

    public void setSeguro(boolean seguro) {
        this.seguro = seguro;
    }
    
    @Override
    public String toString() {
        return this.destino.toString() + "," + this.peso;
    }

    @Override
    public int compareTo(Arco t) {
        if (this.peso < t.getPeso()) {
            return -1;
        }
        if (this.peso > t.getPeso()) {
            return 1;
        }
        return 0;
    }
}

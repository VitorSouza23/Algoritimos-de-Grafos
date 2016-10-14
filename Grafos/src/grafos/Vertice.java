/*
    Arquivo versão 2.0
    Contempla o uso de buscas e árvores mínimas geradoras
*/

package grafos;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe para abstrair vértices de grafos direcionados
 * IFSC - Lages
 * Prof. Vilson Heck Junior
*/
public class Vertice {
    
    //Lista de arcos que saem do vértice
    private final ArrayList<Arco> arcos = new ArrayList();
    
    //Rótulo do vértice: serve para identificação
    private final String rotulo;
    
//Os dois próximos atributos são utilizados pelos algoritmos de grafos.
    
    //Quando o valor de visitado for 0 (zero) significa que o vértice ainda
    //não foi visitado pelo algoritmo. Em cada nova visita o método deve invocar
    //o método visitar() para incrementar este valor. O método zerarVisitas()
    //zera este valor novamente. O método obterVisitado() informa o valor atual.
    private int visitado = 0;
    
    //Vários algoritmos irão medir a distância de um vértice até outro. Este
    //atributo servirá como um "medidor auxiliar de distância", armazenando
    //temporariamente distâncias nas iterações dos algoritmos. Os métodos
    //definirDistancia(), zerarDistancia() e obterDistancia() devem ser usados.
    private double distancia = Double.POSITIVE_INFINITY;
    
    public Vertice(String rotulo) {
        this.rotulo = rotulo;
    }
    
    public void adicionarArco(Vertice destino, double peso) {
        this.arcos.add(new Arco(this, destino, peso));
    }
    
    public boolean removerConexao(Vertice destino) {
        for (Arco arcoAtual : arcos) {
            if (arcoAtual.getDestino() == destino) {
                this.arcos.remove(arcoAtual);
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<Arco> obterArcos() {
        return this.arcos;
    }

    @Override
    public String toString() {
        return this.rotulo;
    }
    
    @Override
    public boolean equals(Object o) {
        return o.toString().equals(this.rotulo);
    }
    
    public String obterLinhaArquivo() {
        String linha = this.rotulo;
        for (Arco arcoAtual : arcos) {
            linha += "\t" + arcoAtual.toString();
        }
        return linha;
    }
    
    public int obterGrau() {
        return this.arcos.size();
    }
    
    public void visitar() {
        this.visitado++;
    }
    
    public int obterVisitado() {
        return this.visitado;
    }
    
    public void zerarVisitas() {
        this.visitado = 0;
    }
    
    public void zerarDistancia() {
        this.distancia = Double.POSITIVE_INFINITY;
    }
    
    public void definirDistancia(double distancia) {
        this.distancia = distancia;
    }
    
    public double obterDistancia() {
        return this.distancia;
    }
    
    private int nArvore;

    public int getnArvore() {
        return nArvore;
    }

    public void setnArvore(int nArvore) {
        this.nArvore = nArvore;
    }
    
    /*public void ordenaArcos() {
        for (int i = 0; i < arcos.size() - 1; i++) {
            for (int j = i + 1; j < arcos.size(); j++) {
                if (arcos.get(i).getPeso() > arcos.get(j).getPeso()) {
                    Collections.swap(arcos, i, j);
                }
            }
        }
    }*/
}

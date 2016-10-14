package grafos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Nesta classe devem ser implementados todos os métodos de grafos de forma
 * estática
 *
 * @author vilson.junior
 */
public class Algoritmos {

    public static ArrayList<Vertice> percorreLargura(Grafo g, Vertice inicio) {
        ArrayList<Vertice> resultado = new ArrayList();
        for (Vertice vertice : g.obterVertices()) {
            vertice.zerarVisitas();
            vertice.zerarDistancia();
        }
        inicio.visitar();
        inicio.definirDistancia(0);
        resultado.add(inicio);
        Queue<Vertice> fila = new LinkedList<>();
        fila.add(inicio);
        while (!fila.isEmpty()) {
            Vertice u = fila.remove();
            for (Arco arco : u.obterArcos()) {
                Vertice adj = arco.getDestino();
                if (adj.obterVisitado() == 0) {
                    adj.visitar();
                    adj.definirDistancia(u.obterDistancia() + arco.getPeso());
                    resultado.add(adj);
                    fila.add(adj);
                }
            }
            u.visitar();
        }

        return resultado;
    }

    public static ArrayList<Vertice> percorreProfundidade(Grafo g, Vertice inicio) {
        ArrayList<Vertice> resultado = new ArrayList();
        for (Vertice vertice : g.obterVertices()) {
            vertice.zerarVisitas();
            vertice.zerarDistancia();
        }
        inicio.visitar();
        inicio.definirDistancia(0);
        resultado.add(inicio);
        Stack<Vertice> pilha = new Stack<>();
        pilha.push(inicio); 
        while (!pilha.isEmpty()) {
            Vertice u = pilha.peek();
            Vertice adj = null;
            double peso = 0;
            for (Arco arco : u.obterArcos()) {
                Vertice aux = arco.getDestino();
                if (aux.obterVisitado() == 0) {
                    adj = aux;
                    peso = arco.getPeso();
                    break;
                }
            }
            if (adj != null) {
                adj.visitar();
                adj.definirDistancia(u.obterDistancia() + peso);
                resultado.add(adj);
                pilha.push(adj);
            } else {
                u.visitar();
                pilha.pop();
            }

        }

        return resultado;
    }

    public static ArrayList<Vertice> percorreProfundidadeRecursiva(Grafo g, Vertice inicio) {
        ArrayList<Vertice> resultado = new ArrayList();
        for (Vertice vertice : g.obterVertices()) {
            vertice.zerarVisitas();
            vertice.zerarDistancia();
        }
        inicio.definirDistancia(0);
        resultado.add(inicio);
        recursaoProfundidade(g, inicio, resultado);
        return resultado;
    }

    private static void recursaoProfundidade(Grafo g, Vertice vertice, ArrayList<Vertice> resultado) {
        vertice.visitar();
        for (Arco arco : vertice.obterArcos()) {
            Vertice adj = arco.getDestino();
            if (adj.obterVisitado() == 0) {
                adj.visitar();
                adj.definirDistancia(vertice.obterDistancia() + arco.getPeso());
                resultado.add(adj);
                recursaoProfundidade(g, adj, resultado);
            }
        }
        vertice.visitar();
    }

    
    public static void arvoreGeradoraMinima(Grafo g){
        int n = 0;
        for(Vertice vertice : g.obterVertices()){
            vertice.setnArvore(n++);
        }
        
        ArrayList<Arco> arestas = g.obterTodosOsArcos();
        
        
       
        Collections.sort(arestas);
        
        
        
        for(Arco aresta : arestas){
            if(aresta.getOrigem().getnArvore()  == aresta.getDestino().getnArvore()){
                aresta.getOrigem().removerConexao(aresta.getDestino());
            }else{
                int numeroArvore = aresta.getDestino().getnArvore();
                for(Vertice vertice : g.obterVertices()){
                    if(vertice.getnArvore() == numeroArvore){
                        vertice.setnArvore(aresta.getOrigem().getnArvore());
                    }
                }
                
            }
        }
        
        
        try {
            g.gravarArquivo(new File("novografo.grf"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
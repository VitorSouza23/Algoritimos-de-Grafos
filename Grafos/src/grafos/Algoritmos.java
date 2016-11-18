package grafos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;
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

    public static void arvoreGeradoraMinima(Grafo g) {
        int n = 0;
        for (Vertice vertice : g.obterVertices()) {
            vertice.setnArvore(n++);
        }

        ArrayList<Arco> arestas = g.obterTodosOsArcos();

        Collections.sort(arestas);

        for (Arco aresta : arestas) {
            if (aresta.getOrigem().getnArvore() == aresta.getDestino().getnArvore()) {
                aresta.getOrigem().removerConexao(aresta.getDestino());
            } else {
                int numeroArvore = aresta.getDestino().getnArvore();
                for (Vertice vertice : g.obterVertices()) {
                    if (vertice.getnArvore() == numeroArvore) {
                        vertice.setnArvore(aresta.getOrigem().getnArvore());
                    }
                }

            }
        }

        arestas = g.obterTodosOsArcos();
        for (Arco aresta : arestas) {
            Vertice origem = aresta.getOrigem();
            Vertice destino = aresta.getDestino();
            destino.adicionarArco(origem, aresta.getPeso());
        }

        try {
            g.gravarArquivo(new File("novografo.grf"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static ArrayList<Vertice> algoritimoDijkstra(Grafo g, Vertice inicio) {
        ArrayList<Vertice> resultado = new ArrayList<>();
        for (Vertice vertice : g.obterVertices()) {
            vertice.zerarDistancia();
            vertice.setCaminho("");
        }
        inicio.definirDistancia(0);
        ArrayList<Vertice> filaDePrioridade = new ArrayList<>();

        for (Vertice v : g.obterVertices()) {
            filaDePrioridade.add(v);
        }

        while (!filaDePrioridade.isEmpty()) {
            Vertice u = null;
            double menorDistancia = Double.POSITIVE_INFINITY;
            int indiceMenorDistancia = -1;
            for (int x = 0; x < filaDePrioridade.size(); x++) {
                if (filaDePrioridade.get(x).obterDistancia() <= menorDistancia) {
                    menorDistancia = filaDePrioridade.get(x).obterDistancia();
                    indiceMenorDistancia = x;
                }
            }
            u = filaDePrioridade.remove(indiceMenorDistancia);
            resultado.add(u);
            for (Arco arco : u.obterArcos()) {
                Vertice v = arco.getDestino();
                if (v.obterDistancia() > u.obterDistancia() + arco.getPeso()) {
                    v.definirDistancia(u.obterDistancia() + arco.getPeso());
                    v.setCaminho(u.getCaminho());
                }
            }
        }
        return resultado;
    }

    private static boolean buscaResidual(Grafo g, Vertice inicio, Vertice fim) {
        for (Vertice vertice : g.obterVertices()) {
            vertice.zerarVisitas();
            vertice.setCaminhoLista(null);
        }
        inicio.visitar();
        Queue<Vertice> fila = new LinkedList<>();
        fila.add(inicio);
        while (!fila.isEmpty()) {
            Vertice u = fila.remove();
            for (Arco arco : u.obterArcos()) {
                Vertice adj = arco.getDestino();
                if (adj.obterVisitado() == 0 && arco.getFluxo() < arco.getPeso()) {
                    adj.visitar();
                    ArrayList<Arco> caminho = u.getCaminhoLista();
                    caminho.add(arco);
                    adj.setCaminhoLista(caminho);
                    fila.add(adj);
                    if (adj.equals(fim)) {
                        
                        return true;
                    }
                }
            }
            u.visitar();
        }

        return false;
    }

    public static double fluxoMaximo(Grafo g, Vertice inicio, Vertice fim) {
        double fluxoMaximo = 0;
        for (Arco arco : g.obterTodosOsArcos()) {
            arco.setFluxo(0);
        }
        System.out.println("De " + inicio.toString() + " Para: " + fim.toString());
        System.out.println("=============================================");
        while (buscaResidual(g, inicio, fim)) {
            double max = Double.MAX_VALUE;
            for (Arco arco : fim.getCaminhoLista()) {
                max = max > arco.getPeso()? arco.getPeso() : max; 
            }
            for(Arco arco: fim.getCaminhoLista()){
                 if (arco.getPeso() >= max + arco.getFluxo()) {
                    arco.setFluxo(arco.getFluxo() + max);
                }
            }
            for (Arco arco : fim.getCaminhoLista()) {
                System.out.println("Origem: " + arco.getOrigem() + " Destino: " + arco.getDestino() + " Fluxo: " + arco.getPeso()
                        + "/" + arco.getFluxo());
                if(arco.getDestino().equals(fim)){
                    System.out.println("Gargalo: " + max);
                    System.out.println("=============================================");
                }
            }
            fluxoMaximo += max;
        }
        System.out.println("Fluxo Máximo: " + fluxoMaximo);
        return fluxoMaximo;
    }

}

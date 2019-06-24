package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	private Graph<Airport,DefaultWeightedEdge> grafo;
	ExtFlightDelaysDAO dao;
	private Map<Integer,Airport>Airportmap;
	private List<Airport> soluzione;
	private double best=0;
	
	public Model() {
		dao=new ExtFlightDelaysDAO();
		Airportmap=new HashMap<Integer,Airport>();
		dao.loadAllAirports(Airportmap);
		
	}
	
	
	
	public Graph<Airport, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}



	public Map<Integer, Airport> getAirportmap() {
		return Airportmap;
	}



	public void creaGrafo(int x) {
		List<AirportCount>l=new ArrayList<AirportCount>();
		l.clear();
		for(Airport air:this.Airportmap.values()) {
			l.add(dao.loadAllAirportsCount(air));
		}
		
		grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		for(AirportCount a: l) {
				if(a.getCount()>x) {		
			grafo.addVertex(a.getAirport());
		}
		}
		
		for(Airport a1:grafo.vertexSet()) {
			for(Airport b:grafo.vertexSet()) {
				if(!a1.equals(b) && !grafo.containsEdge(grafo.getEdge(a1, b))&& !grafo.containsEdge(grafo.getEdge(b,a1))) {
					Graphs.addEdge(grafo, a1, b, dao.setArchiPeso(a1, b));
				}
			}
		}
	
		
	}
	
	public List<Airport> getAeroportiAdiacenti(Airport a, Graph<Airport,DefaultWeightedEdge> grafo) {
		List<Airport> list=new ArrayList<>();
		for(Airport a1:Graphs.neighborListOf(grafo, a)) {
			a1.setVoliTotali(dao.numeroVoli(a1));
			list.add(a1);
		}
		Collections.sort(list);
		return list ;
	}

	public void genera(int t,Airport a, Airport b) {
		soluzione=new ArrayList<Airport>();
		List<Airport> list=new ArrayList<Airport>();
		list.add(a);
		this.ricorsione(list,0,a,b,t);
	}
	
	public void ricorsione(List<Airport> parziale,int level,Airport a, Airport b,int t) {
		if(parziale.get(parziale.size()-1).equals(b) && (parziale.size()-1)<=t && calcolaPunteggio(parziale)>best) {
			best=calcolaPunteggio(parziale);
			soluzione.clear();
			soluzione.addAll(parziale);
			return;
		}
		List<Airport> vicini=new LinkedList<Airport>();
		vicini.clear();
		vicini.addAll(Graphs.neighborListOf(grafo,parziale.get(parziale.size()-1) ));
		for(int i=0;i<vicini.size();i++) {
			parziale.add(vicini.get(i));
			if(!parziale.subList(0, parziale.size()-1).contains(vicini.get(i)) && level<t && grafo.containsEdge(parziale.get(level),vicini.get(i))) {
		this.ricorsione(parziale, level+1, a, b, t);
			}
			parziale.remove(level+1);
		}
	}
	
	public double calcolaPunteggio(List<Airport>l) {
		double punti=0.0;
		for(int i=0;i<l.size()-2;i++) {
			if(grafo.containsEdge(l.get(i), l.get(i+1)) ){
			punti+=grafo.getEdgeWeight(grafo.getEdge(l.get(i), l.get(i+1)));
		}
			else {
				punti+=grafo.getEdgeWeight(grafo.getEdge(l.get(i+1), l.get(i)));
			}
			}
		return punti;
	}
	
	public List<Airport> getSoluzione(){
		return soluzione;
	}
	
	public boolean isConnesso(Airport a,Airport b) {
		BreadthFirstIterator<Airport,DefaultWeightedEdge> it=new BreadthFirstIterator<Airport,DefaultWeightedEdge>(grafo,a);
		boolean result=false;
		while(it.hasNext()) {
			if(it.next().equals(b)) {
				result=true;
			}
		}
		
		return result;
	}
	
}

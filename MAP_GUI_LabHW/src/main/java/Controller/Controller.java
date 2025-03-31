package Controller;

import Model.ADTs.*;
import Model.MyException;
import Model.PrgState;
import Model.Stmt.CompStmt;
import Model.Stmt.IStmt;
import Model.Value.IValue;
import Model.Value.RefValue;
import Repo.IRepo;

import java.util.*;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
	private final IRepo repo;
	private ExecutorService executor;

	public Controller(IRepo repo) {
		this.repo = repo;
	}

	Map<Integer, IValue> garbageCollector(Set<Integer> symTableAddr, Map<Integer,IValue> heap){
		return heap.entrySet().stream()
				.filter(e -> symTableAddr.contains(e.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	Set<Integer> getAddrFromSymTable(Collection<IValue> symTableValues, Map<Integer, IValue> heap){
		// returns a set of all addresses that are referenced in the symTable (directly or indirectly)
		// repo.log("SymTable values: " + symTableValues);
		Set<Integer> returnSet = new TreeSet<>();

		symTableValues.forEach(v -> {
					while (v instanceof RefValue) {
						returnSet.add(((RefValue)v).getAddr());
						v = heap.get(((RefValue)v).getAddr());
					}
				});

		// repo.log("Return set: " + returnSet);
		return returnSet;
	}

	public void allSteps() {
		executor = Executors.newFixedThreadPool(2);
		//remove the completed programs
		List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
		while(!prgList.isEmpty()) {
			oneStepForAll();
			prgList = removeCompletedPrg(repo.getPrgList());
		}

		executor.shutdownNow();

		// update the repository state
		// repo.setPrgList(prgList);
	}

	List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
		return inPrgList.stream()
			.filter(PrgState::isNotCompleted)
			.collect(Collectors.toList());
	}

	public void oneStepForAll() {
		List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());

		if (prgList.isEmpty()) {
			return;
		}

		IDict<Integer, IValue> heap = prgList.getFirst().getHeapTable();
		IDict<String, IValue> symTable = new Dict<>();

		// building a symbol table with all references
		int i = 0;
		for (PrgState prg: prgList) {
			IDict<String, IValue> st = prg.getSymTable();
			for (String s : st.keySet()) {
				IValue value = st.get(s);
				if(value instanceof RefValue) {
					symTable.put(Integer.toString(i), st.get(s)); // the key doesn't matter, but it shouldn't repeat itself
					i++;
				}
			}
		}
		// garbage collector
		heap.setContent(
				garbageCollector(
						getAddrFromSymTable(
								symTable.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList()),
								heap.getContent()
						),
						heap.getContent()
				)
		);

		if (executor == null || executor.isShutdown()) {
			executor = Executors.newFixedThreadPool(2);
			oneStepForAllPrg(prgList);
			executor.shutdownNow();
		}
		else {
			oneStepForAllPrg(prgList);
		}
	}

	void oneStepForAllPrg(List<PrgState> prgList) {
		//prepare the list of callables
		List<Callable<PrgState>> callList = prgList.stream()
			.map((PrgState p) -> (Callable<PrgState>)(() -> p.oneStep()))
			.toList();

		try {
			//start the execution of the callables
			//it returns the list of new created PrgStates (namely threads)
			List<PrgState> newPrgList = executor.invokeAll(callList).stream()
				.map(future -> {
					try {
						return future.get();
					} catch (MyException | InterruptedException | ExecutionException e) {
						repo.log(e.getMessage());
						System.out.println(e.getMessage());
						return null;
					}
				})
				.filter(p -> p != null)
				.toList();

			//add the new created threads to the list of existing threads
			prgList.addAll(newPrgList);

			//after the execution, print the PrgState List into the log file
			prgList.forEach(repo::logPrgStateExec);

			//Save the current programs in the repository
			if(!prgList.isEmpty())
				repo.setPrgList(prgList);
			//repo.updatePrgList(prgList);
		}
		catch (InterruptedException e) {
			repo.log(e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	public IRepo getRepo() {
		return repo;
	}
}

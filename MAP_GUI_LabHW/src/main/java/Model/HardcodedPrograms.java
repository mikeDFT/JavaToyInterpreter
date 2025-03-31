package Model;

import Model.ADTs.*;
import Model.Stmt.*;
import Model.Type.*;
import Model.Expr.*;
import Model.Value.*;


import java.util.ArrayList;
import java.util.Arrays;


public class HardcodedPrograms {
	public static final java.util.List<IStmt> hardcodedPrograms = new ArrayList<>(java.util.List.of(
			/*
			int v
			v = 2
			print(v)
			 */
			new CompStmt(
					new CompStmt(
						new VarDeclStmt("v",new IntType()),
						new AssignStmt("v",
							new ValueExpr(new IntValue(2)))
					),
					new PrintStmt(new VarExpr("v"))
			),


			/*
			int a
			a = 2+(3*5)
			int b
			b =
			 */
			new CompStmt(new VarDeclStmt("a",new IntType()),
					new CompStmt(new AssignStmt("a",new ArithExpr(
							new ValueExpr(new IntValue(2)),
							new ArithExpr(
									new ValueExpr(new IntValue(3)),
									new ValueExpr(new IntValue(5)),"*"),"+")
							),
							new CompStmt(new VarDeclStmt("b",new IntType()),
									new CompStmt(new AssignStmt("b",
											new ArithExpr(new ArithExpr(new VarExpr("a"),
													new ArithExpr(new ValueExpr(new IntValue(4)),
															new ValueExpr(new IntValue(2)),"/"),"-"),
													new ValueExpr(new IntValue(7)),"+")),
											new PrintStmt(new VarExpr("b"))
									)
							)
					)
			),

			new CompStmt(new VarDeclStmt("T", new BoolType()),
					new CompStmt(new VarDeclStmt("F", new BoolType()),
								new CompStmt(new AssignStmt("T", new ValueExpr(new BoolValue(true))),
										new CompStmt(
											new AssignStmt("F", new LogicExpr(
													new ValueExpr(new BoolValue(false)), new VarExpr("T"), "and")
											),
											new PrintStmt(new LogicExpr(new VarExpr("T"), new VarExpr("F"), "or"))
										)
								)
							)
					),

			/*
			string varf;
			int varc;
			varf="test.in";
			openRFile(varf);
			readFile(varf,varc);
			print(varc);
			readFile(varf,varc);
			print(varc)
			closeRFile(varf)
			 */
			new CompStmt(
				new VarDeclStmt("varf", new StringType()),
			new CompStmt(
				new VarDeclStmt("varc", new IntType()),
			new CompStmt(
				new AssignStmt("varf", new ValueExpr(new StringValue("test.in"))),
			new CompStmt(
				new OpenRFileStmt(new VarExpr("varf")),
			new CompStmt(
				new ReadFileStmt(new VarExpr("varf"), "varc"),
			new CompStmt(
				new PrintStmt(new VarExpr("varc")),
			new CompStmt(
				new ReadFileStmt(new VarExpr("varf"), "varc"),
			new CompStmt(
				new PrintStmt(new VarExpr("varc")),
				new CloseRFileStmt(new VarExpr("varf"))
			)))))))),

			// STMT 5 (test new)

			// Ref int v;
			// Ref Ref int a;
			// new(v,20);
			// new(a,v);
			// print(v);
			// print(a)
			new CompStmt(
					new VarDeclStmt("v", new RefType(new IntType())),
			new CompStmt(
					new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
			new CompStmt(
					new HeapNewStmt("v", new ValueExpr(new IntValue(20))),
			new CompStmt(
					new HeapNewStmt("a", new VarExpr("v")),
			new CompStmt(
					new PrintStmt(new VarExpr("v")),
					new PrintStmt(new VarExpr("a")
			)))))),

			// STMT 6 (test new and rH)

			// Ref int v;
			// Ref Ref int a;
			// new(v,20);
			// new(a,v);
			// print(rH(v));
			// print(rH(rH(a)))
			new CompStmt(
					new VarDeclStmt("v", new RefType(new IntType())),
			new CompStmt(
					new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
			new CompStmt(
					new HeapNewStmt("v", new ValueExpr(new IntValue(20))),
			new CompStmt(
					new HeapNewStmt("a", new VarExpr("v")),
			new CompStmt(
					new PrintStmt(new HeapReadExpr(new VarExpr("v"))),
					new PrintStmt(new HeapReadExpr(new HeapReadExpr(new VarExpr("a")))
			)))))),

			// STMT 7 (test new, rH and wH)

			// Ref int v;
			// new(v,20);
			// print(rH(v))
			// wH(v,30)
			// print(rH(v)+5)
			new CompStmt(
					new VarDeclStmt("v", new RefType(new IntType())),
			new CompStmt(
					new HeapNewStmt("v", new ValueExpr(new IntValue(20))),
			new CompStmt(
					new PrintStmt(new HeapReadExpr(new VarExpr("v"))),
			new CompStmt(
					new HeapWriteStmt("v", new ValueExpr(new IntValue(30))),
					new PrintStmt(new ArithExpr(new HeapReadExpr(new VarExpr("v")), new ValueExpr(new IntValue(5)), "+")
			))))),

			// STMT 8 (test garbage collector - collects them because not used anymore)

			// Ref int v;
			// new(v,20);
			// new(v,30);
			// new(v,600);
			// wH(v,40)
			// print(rH(v)+6)
			new CompStmt(
					new VarDeclStmt("v", new RefType(new IntType())),
			new CompStmt(
					new HeapNewStmt("v", new ValueExpr(new IntValue(20))),
			new CompStmt(
					new HeapNewStmt("v", new ValueExpr(new IntValue(30))),
			new CompStmt(
					new HeapNewStmt("v", new ValueExpr(new IntValue(600))),
			new CompStmt(
					new HeapWriteStmt("v", new ValueExpr(new IntValue(40))),
					new PrintStmt(new ArithExpr(new HeapReadExpr(new VarExpr("v")), new ValueExpr(new IntValue(6)), "+")
			)))))),

			// STMT 9 (test garbage collector - no longer unsafe,
			// a's double reference is not collected, even after v is reassigned and
			// there is no direct link from SymTable to the first address)

			// Ref int v;
			// Ref Ref int a;
			// new(v,20);
			// new(a,v);
			// new(v,30);
			// print(rH(v))
			// print(rH(rH(a)))
			new CompStmt(
					new VarDeclStmt("v", new RefType(new IntType())),
			new CompStmt(
					new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
			new CompStmt(
					new HeapNewStmt("v", new ValueExpr(new IntValue(20))),
			new CompStmt(
					new HeapNewStmt("a", new VarExpr("v")),
			new CompStmt(
					new HeapNewStmt("v", new ValueExpr(new IntValue(30))),
			new CompStmt(
					new PrintStmt(new HeapReadExpr(new VarExpr("v"))),
					new PrintStmt(new HeapReadExpr(new HeapReadExpr(new VarExpr("a")))
			))))))),

			// STMT 10 (test heap with strings)

			// ref(String) x;
			// String y = "Hello";
			// new(x, y);
			// print(rH(x))
			new CompStmt(new VarDeclStmt("x", new RefType(new StringType())),
					new CompStmt(new VarDeclStmt("y", new StringType()),
							new CompStmt(new AssignStmt("y", new ValueExpr(new StringValue("Hello"))),
									new CompStmt(new HeapNewStmt("x", new VarExpr("y")),
											new PrintStmt(new HeapReadExpr(new VarExpr("x"))))))),

			// STMT 11 (test while)
			// int x;
			// x = 10;
			// while (x > 0) {
			// 		print(x);
			// 		x = x - 1;
			// }
			// print(x)
			new CompStmt(
					new VarDeclStmt("x", new IntType()),
			new CompStmt(
					new AssignStmt("x", new ValueExpr(new IntValue(10))),
			new CompStmt(
					new WhileStmt(new RelExpr(new VarExpr("x"), new ValueExpr(new IntValue(0)), ">"),
							new CompStmt(
									new PrintStmt(new VarExpr("x")),
									new AssignStmt("x", new ArithExpr(new VarExpr("x"), new ValueExpr(new IntValue(1)), "-"))
							)
					),
					new PrintStmt(new VarExpr("x"))
			))),


			// STMT 12
			//  int v;
			//  Ref int a;
			//  v=10;new(a,22);
			//  fork(
				//  wH(a,30);
				//  v=32;
				//  print(v);
				//  print(rH(a))
			//  );
			//  print(v);
			//  print(rH(a))
			new CompStmt(
				new VarDeclStmt("v", new IntType()),
			new CompStmt(
				new VarDeclStmt("a", new RefType(new IntType())),
			new CompStmt(
				new AssignStmt("v", new ValueExpr(new IntValue(10))),
			new CompStmt(
				new HeapNewStmt("a", new ValueExpr(new IntValue(10))),
			new CompStmt(
				new ForkStmt(
					new CompStmt(
						new HeapWriteStmt("a", new ValueExpr(new IntValue(30))),
					new CompStmt(
						new AssignStmt("v", new ValueExpr(new IntValue(32))),
					new CompStmt(
						new PrintStmt(new VarExpr("v")),
						new PrintStmt(new HeapReadExpr(new VarExpr("a")))
					)))
				),
			new CompStmt(
				new PrintStmt(new VarExpr("v")),
				new PrintStmt(new HeapReadExpr(new VarExpr("a"))))
		))))),

		// STMT 13
		// int v;
		// ref int b;
		// new(b, 1)
		// fork(
		//   ref int a;
		//   new(a, 30);
		//   print(rH(b))
		//   print(rH(a))
		// )
		// fork(
		//   ref int a;
		//   new(a, 40);
		//   new(b, 100)
		//   print(rH(a))
		// )
		// ref int a;
		// new(a, 50);
		// new(b, 60);
		// print(rH(a))
		new CompStmt(
			new VarDeclStmt("v", new IntType()),
		new CompStmt(
			new VarDeclStmt("b", new RefType(new IntType())),
		new CompStmt(
			new HeapNewStmt("b", new ValueExpr(new IntValue(1))),
		new CompStmt(
			new ForkStmt(
				new CompStmt(
					new VarDeclStmt("a", new RefType(new IntType())),
				new CompStmt(
					new PrintStmt(new HeapReadExpr(new VarExpr("b"))),
					//new HeapWriteStmt("b", new ValueExpr(new IntValue(90))),
				new CompStmt(
					new HeapNewStmt("a", new ValueExpr(new IntValue(30))),
					new PrintStmt(new HeapReadExpr(new VarExpr("a")))
				)))
			),
		new CompStmt(
			new ForkStmt(
				new CompStmt(
					new VarDeclStmt("a", new RefType(new IntType())),
				new CompStmt(
					new HeapWriteStmt("b", new ValueExpr(new IntValue(100))),
				new CompStmt(
					new HeapNewStmt("a", new ValueExpr(new IntValue(40))),
					new PrintStmt(new HeapReadExpr(new VarExpr("a")))
				)))
			),
		new CompStmt(
			new VarDeclStmt("a", new RefType(new IntType())),
		new CompStmt(
			new HeapNewStmt("a", new ValueExpr(new IntValue(50))),
		new CompStmt(
			new HeapWriteStmt("b", new ValueExpr(new IntValue(60))),
			new PrintStmt(new HeapReadExpr(new VarExpr("a")))
		)))))))),

		// STMT 14 -- should get picked up by type checker
		/*
		string v
		v = 2
		print(v)
		 */
		new CompStmt(
		new CompStmt(
			new VarDeclStmt("v",new StringType()),
			new AssignStmt("v", new ValueExpr(new IntValue(2)))),
			new PrintStmt(new VarExpr("v"))
		),

		// STMT 15 -- should get picked up by type checker
		/*
		ref int v
		wh(v, True)
		 */
		new CompStmt(
		new VarDeclStmt("v", new RefType(new StringType())),
		new HeapWriteStmt("v", new ValueExpr(new BoolValue(true)))),

		// STMT 16 -- should get picked up by type checker
		/*
		ref int v
		v = 2
		print(v)
		 */
		new CompStmt(
		new CompStmt(
			new VarDeclStmt("v", new RefType(new StringType())),
			new AssignStmt("v", new ValueExpr(new IntValue(2)))),
			new PrintStmt(new VarExpr("v"))
		),

		// STMT 16 -- should get picked up by type checker
		/*
		openRFile(True)
		readRFile(True, v)
		closeRFile(3)
		 */
		new CompStmt(
		new CompStmt(
			new OpenRFileStmt(new ValueExpr(new BoolValue(true))),
			new ReadFileStmt(new ValueExpr(new BoolValue(true)), "v")),
			new CloseRFileStmt(new ValueExpr(new IntValue(3)))
		),

		// STMT 15 (with comp statements at the beginning to show stack)
		// int v;
		// ref int b;
		// new(b, 1)
		// fork(
		//   ref int a;
		//   new(a, 30);
		//   print(rH(b))
		//   print(rH(a))
		// )
		// fork(
		//   ref int a;
		//   new(a, 40);
		//   new(b, 100)
		//   print(rH(a))
		// )
		// ref int a;
		// new(a, 50);
		// new(b, 60);
		// print(rH(a))
		new CompStmt(
		new CompStmt(
		new CompStmt(
		new CompStmt(
		new CompStmt(
			new VarDeclStmt("v", new IntType()),
			new VarDeclStmt("b", new RefType(new IntType()))),
			new HeapNewStmt("b", new ValueExpr(new IntValue(1)))),
			new ForkStmt(
				new CompStmt(
				new CompStmt(
				new CompStmt(
					new VarDeclStmt("a", new RefType(new IntType())),
					new PrintStmt(new HeapReadExpr(new VarExpr("b")))),
					new HeapNewStmt("a", new ValueExpr(new IntValue(30)))),
					new PrintStmt(new HeapReadExpr(new VarExpr("a"))))
			)),
			new ForkStmt(
				new CompStmt(
				new CompStmt(
				new CompStmt(
					new VarDeclStmt("a", new RefType(new IntType())),
					new HeapWriteStmt("b", new ValueExpr(new IntValue(100)))),
					new HeapNewStmt("a", new ValueExpr(new IntValue(40)))),
					new PrintStmt(new HeapReadExpr(new VarExpr("a"))))
			)),
			new CompStmt(
			new CompStmt(
			new CompStmt(
				new VarDeclStmt("a", new RefType(new IntType())),
				new HeapNewStmt("a", new ValueExpr(new IntValue(50)))),
				new HeapWriteStmt("b", new ValueExpr(new IntValue(60)))),
				new PrintStmt(new HeapReadExpr(new VarExpr("a")))
			)
	)));}


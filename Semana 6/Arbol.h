#include <stdio.h>
#ifndef __ARBOL_HPP__
#define __ARBOL_HPP__
template<typename X>
struct Nodo {
	X e;//elemento
	float p;//peso
	Nodo<X>* hi;
	Nodo<X>* hd;
	Nodo(X e, float p) {
		hi = nullptr;
		hd = nullptr;
		this->p = p;
		this->e = e;
	}
};

template<typename X>
struct Arbol {
	Nodo<X>* r;//raiz
	long nro;
	Arbol() {
		r = nullptr;
		nro = 0;
	}
	void insertar(X e1, float p1, Nodo<X>*& n) {
		if (n == nullptr)
			n = new Nodo<X>(e1, p1);
		else if (p1 <= n->p)
			insertar(e1, p1, n->hi);
		else if (p1 > n->p)
			insertar(e1, p1, n->hd);
		++nro;
	}
	void insertar(X e1, float p1) {
		insertar(e1, p1, r);
	}
	void mostrar() {//en orden
		mostrar(r);
	}
	void mostrar(Nodo<X>* n) {//en orden
		if (n == nullptr) return;
		else
		{
			mostrar(n->hi);
			printf("Nodo: %i Peso: %f \n",n->e, n->p);
			mostrar(n->hd);
		}
	}
  float calcularPesos(){
    return  calcularPesos(r); 
  }
  float calcularPesos(Nodo<X>* n){
    float t=1;    
   if(n==nullptr)
   return 1;
   else
      {
        t=t*calcularPesos(r->hi);
      printf("Totalizado: %f \n",t);
      }
return t;
  }
};
#endif
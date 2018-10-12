#include "Arbol.h"
#include <stdio.h>
#include <time.h>
#include <stdlib.h>
int main() {
 // printf("hola");
 srand(time(NULL));
 Arbol<int> arbol;
 for(int i=0;i<=100;i++)
  arbol.insertar(rand()%100, rand()%10000);
 arbol.mostrar();
}
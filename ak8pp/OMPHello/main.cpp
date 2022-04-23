#include <iostream>
#include "omp.h"

using namespace std;

int main()
{
    cout << "Hello World!" << endl;

    //Testovaci globalni pole
    int a[8] = {10,20,30,40,50,60,70,80};

    // Globalni neiniciovana promenna
    int i;

    // Pocet mych logickych procesoru je 8 - TaskManager

    // num_threads(12) - specifikuje pocet vlaken explicitne, jinak se posle na vsechny procesory (8)
    // private(i) - i nebude globalni ale je tedy privatni pro kazde vlakno, promenna tu je neinicializovan!!!
    // firstprivate(a) - kazdy thread ma vlastni obraz a
    // shared(a) - sdilene jako default
    #pragma omp parallel private(i)
    {
        //omp_get_thread_num() - vrati id threadu
        // privatni i se naplni unikatnim identifikatorem threadu
         i =  omp_get_thread_num();

        // pole a je globalni a vsechny thready se k nemu mohou dostat
        printf("Parallel task on CPU with ID %d handling number %d\n", i,a[i]);


        // cout << "Parallel task!" << endl;


        //#pragma omp for - kazdy procesor udela jednu iteraci, pri n/p, tj 5ty proc udela 5tou iteraci,
        //bez tohoto dela kazde vlakno svuj vlastni for
        #pragma omp for
        for(int j=0; j< omp_get_num_procs(); ++j){
            printf("Iteration: %d, CPU ID:%d\n", j,i);
        }
    }

    return 0;
}

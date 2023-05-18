import numpy as np

def dejong1(x):
    return sum([x[i]**2 for i in range(len(x))])

def dejong2(x):
    return 100*(x[0]**2 - x[1])**2 + (1 - x[0])**2

def schwefel(x):
    n = len(x)
    return 418.9829 * n - sum([x[i] * np.sin(np.sqrt(np.abs(x[i]))) for i in range(n)])


def generate_vector():
    # Generate a random vector of five values between -5.12 and 5.12
    vector = np.random.uniform(-5.12, 5.12, 5)
    return vector



D1=5
D2=10
FES=10000


# ----- RandomSearch -----

countOfRuns = 30

for _ in range(countOfRuns):

    pos1 = best1 = np.array(generate_vector())
    pos2 = best2 = np.array(generate_vector())
    pos3 = best3 = np.array(generate_vector())

    pos1_2 = best1_2 = np.array(generate_vector())
    pos2_2 = best2_2 = np.array(generate_vector())
    pos3_2 = best3_2 = np.array(generate_vector())

    #Run for first dimension
    for _ in range(D1):
        for _ in range(FES):

            #Dejong1
            pos1 =  np.array(generate_vector())
            if dejong1(pos1) < dejong1(best1):
                best1 = pos1

            #Dejong2
            pos2 =  np.array(generate_vector())
            if dejong2(pos2) < dejong2(best2):
                best2 = pos2

            #Schwefel
            pos3 =  np.array(generate_vector())
            if schwefel(pos3) < schwefel(best3):
                best3 = pos3
    
    #Run for second dimension
    for _ in range(D2):
        for _ in range(FES):

            #Dejong1
            pos1_2 =  np.array(generate_vector())
            if dejong1(pos1_2) < dejong1(best1_2):
                best1_2 = pos1_2

            #Dejong2
            pos2_2 =  np.array(generate_vector())
            if dejong2(pos2_2) < dejong2(best2_2):
                best2_2 = pos2_2

            #Schwefel
            pos3_2 =  np.array(generate_vector())
            if schwefel(pos3_2) < schwefel(best3_2):
                best3_2 = pos3_2
    
        
print(best1)
print(best2)

# ----- Annealing -----


for _ in range(countOfRuns):

    pos1 = best1 = np.array(generate_vector())
    pos2 = best2 = np.array(generate_vector())
    pos3 = best3 = np.array(generate_vector())
    
    num_dimensions = 5  # Number of dimensions in the search space
    lower_bound = -5.12  # Lower bound of the search space
    upper_bound = 5.12  # Upper bound of the search space
    initial_temperature = 100.0  # Initial temperature for annealing
    cooling_rate = 0.01  # Cooling rate for annealing
    num_iterations = 1000  # Number of iterations for the simulated annealing


    #Run for first dimension
    for _ in range(D1):
        for iteration in range(FES):

            #Dejong1
            temperature = initial_temperature * np.exp(-cooling_rate * iteration)

            candidate_solution =  np.array(generate_vector())
            candidate_fitness = dejong1(candidate_solution)

            if candidate_fitness < current_fitness or np.random.uniform() < np.exp(-(candidate_fitness - current_fitness) / temperature):
                current_solution = candidate_solution
                current_fitness = candidate_fitness

            if candidate_fitness < best_fitness:
                best_solution = candidate_solution
                best_fitness = candidate_fitness

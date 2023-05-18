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



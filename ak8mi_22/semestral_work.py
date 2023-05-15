import numpy as np

def dejong1(x):
    return sum([x[i]**2 for i in range(len(x))])

def generate_vector():
    # Generate a random vector of five values between -5.12 and 5.12
    vector = np.random.uniform(-5.12, 5.12, 5)
    return vector

# Testing the function with a random input vector
x = np.array([1, 2, 3])
print(dejong1(x))  # Output: 14


D1=5
D2=10
FES = 10000

pos1 = best1 = np.array(generate_vector())

# ----- RandomSearch -----

for _ in range(D1):
    for _ in range(FES):
        pos1 =  np.array(generate_vector())

        if dejong1(pos1) < dejong1(best1):
            best1 = pos1
    


pos2 = best2 = np.array(generate_vector())

for _ in range(D2):
    for _ in range(FES):
        pos2 =  np.array(generate_vector())

        if dejong1(pos2) < dejong1(best2):
            best2 = pos2
    

print(best1)
print(best2)

# ----- Annealing -----



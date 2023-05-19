import numpy as np
import matplotlib.pyplot as plt

# Define the cost functions
def dejong_1(x):
    return np.sum(x ** 2, axis=1)

def dejong_2(x):
    return np.sum(np.abs(x), axis=1)

def schweffel(x):
    dimension = x.shape[1]
    return np.sum(-x * np.sin(np.sqrt(np.abs(x))), axis=1) + dimension * 418.9829

# Random Search algorithm
def random_search(cost_function, dimension, max_iterations, runs):
    best_solutions = []
    costs = []
    
    for _ in range(runs):
        best_solution = None
        best_cost = float('inf')
        
        for _ in range(max_iterations):
            solution = np.random.uniform(low=-5, high=5, size=(dimension,))
            cost = cost_function(np.array([solution]))
            
            if cost < best_cost:
                best_solution = solution
                best_cost = cost
        
        best_solutions.append(best_solution)
        costs.append(best_cost)
    
    return best_solutions, costs

# Simulated Annealing algorithm
def simulated_annealing(cost_function, dimension, max_iterations, initial_temperature, cooling_rate, runs):
    best_solutions = []
    costs = []
    
    for _ in range(runs):
        current_solution = np.random.uniform(low=-5, high=5, size=(dimension,))
        current_cost = cost_function(np.array([current_solution]))
        best_solution = current_solution
        best_cost = current_cost
        temperature = initial_temperature
        
        for _ in range(max_iterations):
            for _ in range(10):
                neighbor = current_solution + np.random.uniform(low=-0.1, high=0.1, size=(dimension,))
                neighbor = np.clip(neighbor, -5, 5)
                neighbor_cost = cost_function(np.array([neighbor]))
                
                if neighbor_cost < best_cost:
                    best_solution = neighbor
                    best_cost = neighbor_cost
                
                if neighbor_cost < current_cost or np.random.uniform() < np.exp((current_cost - neighbor_cost) / temperature):
                    current_solution = neighbor
                    current_cost = neighbor_cost
            
            temperature *= cooling_rate
        
        best_solutions.append(best_solution)
        costs.append(best_cost)
    
    return best_solutions, costs

# Compute statistics for a list of costs
def compute_statistics(costs):
    min_cost = np.min(costs)
    max_cost = np.max(costs)
    mean_cost = np.mean(costs)
    median_cost = np.median(costs)
    std_dev_cost = np.std(costs)
    
    return min_cost, max_cost, mean_cost, median_cost, std_dev_cost

# Run an algorithm multiple times and compute statistics
def run_algorithm(algorithm, cost_function, dimension, max_iterations, initial_temperature=None, cooling_rate=None, runs=30):
    solutions = []
    costs = []
    
    for _ in range(runs):
        if algorithm == random_search:
            best_solutions, best_costs = algorithm(cost_function, dimension, max_iterations, runs=1)
        elif algorithm == simulated_annealing:
            best_solutions, best_costs = algorithm(cost_function, dimension, max_iterations, initial_temperature, cooling_rate, runs=1)
        
        solutions.append(best_solutions[0])
        costs.append(best_costs[0])
    
    return solutions, costs

# Plot the convergence graphs
def plot_convergence_graphs(algorithm_results, algorithm_name, dimension, function_name):
    solutions, costs = algorithm_results
    
    plt.figure(figsize=(10, 5))
    plt.plot(costs)
    plt.xlabel('Iterations')
    plt.ylabel('Cost')
    plt.title(f'{algorithm_name} - {function_name} (Dimension: {dimension})')
    plt.show()

# Plot the average convergence graphs
def plot_average_convergence_graphs(results, algorithm_names, dimensions, function_names):
    for i, dimension in enumerate(dimensions):
        for j, function_name in enumerate(function_names):
            random_search_results = results[0][i][j]
            simulated_annealing_results = results[1][i][j]
            
            random_search_costs = np.mean(random_search_results[1], axis=0)
            simulated_annealing_costs = np.mean(simulated_annealing_results[1], axis=0)
            
            plt.figure(figsize=(10, 5))
            plt.plot(random_search_costs, label=algorithm_names[0])
            plt.plot(simulated_annealing_costs, label=algorithm_names[1])
            plt.xlabel('Iterations')
            plt.ylabel('Cost')
            plt.legend()
            plt.title(f'Average Convergence - {function_name} (Dimension: {dimension})')
            plt.show()

# Plot the comparison graphs
def plot_comparison_graphs(results, dimensions, function_names):
    algorithm_names = ['Random Search', 'Simulated Annealing']
    
    for i, dimension in enumerate(dimensions):
        for j, function_name in enumerate(function_names):
            random_search_results = results[0][i][j]
            simulated_annealing_results = results[1][i][j]
            
            random_search_costs = np.mean(random_search_results[1])
            simulated_annealing_costs = np.mean(simulated_annealing_results[1])
            
            plt.figure(figsize=(10, 5))
            plt.bar(algorithm_names, [random_search_costs, simulated_annealing_costs])
            plt.ylabel('Average Cost')
            plt.title(f'Comparison - {function_name} (Dimension: {dimension})')
            plt.show()

# Set the parameters
max_iterations = 1000
initial_temperature = 1000
cooling_rate = 0.98
runs = 30

dimensions = [5, 10]
function_names = ['DeJong 1', 'DeJong 2', 'Schweffel']
algorithm_names = ['Random Search', 'Simulated Annealing']

# Run the algorithms and collect the results
results = []
for dimension in dimensions:
    dimension_results = []
    for function_name in function_names:
        cost_function = None
        if function_name == 'DeJong 1':
            cost_function = dejong_1
        elif function_name == 'DeJong 2':
            cost_function = dejong_2
        elif function_name == 'Schweffel':
            cost_function = schweffel
        
        random_search_results = run_algorithm(random_search, cost_function, dimension, max_iterations, runs=runs)
        simulated_annealing_results = run_algorithm(simulated_annealing, cost_function, dimension, max_iterations, initial_temperature, cooling_rate, runs=runs)
        
        dimension_results.append((random_search_results, simulated_annealing_results))
    
    results.append(dimension_results)

# Plot the convergence graphs
for i, dimension in enumerate(dimensions):
    for j, function_name in enumerate(function_names):
        random_search_results = results[i][j][0]
        simulated_annealing_results = results[i][j][1]
        
        plot_convergence_graphs(random_search_results, algorithm_names[0], dimension, function_name)
        plot_convergence_graphs(simulated_annealing_results, algorithm_names[1], dimension, function_name)

# Plot the average convergence graphs
plot_average_convergence_graphs(results, algorithm_names, dimensions, function_names)

# Plot the comparison graphs
plot_comparison_graphs(results, dimensions, function_names)

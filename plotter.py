import os
import pandas as pd
import matplotlib.pyplot as plt

# Directory where results CSVs are stored
csv_directory = "./data_csv"
results_csv = f"{csv_directory}/results.csv"

# Read and process the results.csv file
if not os.path.exists(results_csv):
    print(f"Error: {results_csv} not found.")
    exit()

# Read CSV into DataFrame
df = pd.read_csv(results_csv)

# Strip spaces from column names
df.columns = df.columns.str.strip()

# Ensure results directory exists for saving graphs
results_dir = "./results"
if not os.path.exists(results_dir):
    os.makedirs(results_dir)

# Unique data sizes for iteration
data_sizes = df["DataSize"].unique()

# Generate a performance comparison plot for each data size
for size in data_sizes:
    # Filter data for the current size
    size_data = df[df["DataSize"] == size]

    # Calculate average execution time for each algorithm across all data types
    avg_exec_times = size_data.groupby("SortingAlgo")["ExeTime"].mean().reset_index()

    # Plotting the performance comparison for this data size
    plt.figure(figsize=(10, 6))
    plt.title(f"Performance Comparison for {size} Elements", loc="center")
    plt.xlabel("Sorting Algorithms")
    plt.ylabel("Average Execution Time (ns)")

    # Plot each algorithm's average execution time as a bar chart
    plt.plot(avg_exec_times["SortingAlgo"], avg_exec_times["ExeTime"], marker='o', label=f"Size: {size}")

    # Adding grid and legend
    plt.grid(True)
    plt.legend()

    # Save the plot
    plt.savefig(f"{results_dir}/algo_performance_comparison_{size}.png", dpi=300)
    plt.close()  # Close the plot to avoid overlapping
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.*;

import java.util.ArrayList;
import java.util.*;
import java.util.Random;
import java.text.*;
import java.math.*;
import java.util.regex.*;
public class schedule {
public static int randomOS(int burst) throws FileNotFoundException {
	File file = new File("random.txt");
	Scanner input = new Scanner(file);
	Random rand = new Random();
	
	ArrayList<Integer> int_list = new ArrayList<>();
	int counter = 0;
	while(input.hasNextInt()) {
		int_list.add(input.nextInt());
		counter +=1;
	}
	int rand_1 = rand.nextInt(int_list.size());
	int X = int_list.get(rand_1);
	
	input.close();
	int new_burst = 1 + (X%burst);
	return new_burst;
}
public static int sort(ArrayList<Integer> arrival) {
	for(int i = 0; i<arrival.size()-1; i+=1) {
		
		if(arrival.get(i) <= arrival.get(i+1)) {
			
		}
		else {
			return 0;
		}
	}
		return 1;
	}
public static int sorted_jobs(ArrayList<Integer> ready, Map<Integer,Integer> progress, Map<Integer,Integer> cpu) {
	for(int i = 0; i<ready.size()-1;i+=1) {
		int job_1 = ready.get(i);
		int job_2 = ready.get(i+1);
		if(cpu.get(job_1) - progress.get(job_1) <=  cpu.get(job_2) - progress.get(job_2)) {
			
			
		}
		else {
			return 0;
		}
	}
	return 1;
}
public static void sort_jobs(ArrayList<Integer> ready, Map<Integer,Integer> progress, Map<Integer,Integer> cpu) {
	
	boolean sorted = false;
	while(sorted == false) {
		int temp_job;
		int temp_progress_job;
		int temp_progress;
		int temp_cpu_job;
		int temp_cpu;
		for(int i = 0; i<ready.size()-1;i+=1) {
			int job_1 = ready.get(i);
			int job_2 = ready.get(i+1);
			
			
			if(cpu.get(job_1) - progress.get(job_1) >  cpu.get(job_2) - progress.get(job_2)) {
				temp_job = job_1;
				
				ready.set(i, job_2);
				ready.set(i+1, temp_job);
				
			}
		}
		int x = sorted_jobs(ready,progress,cpu);
		if(x == 1) {
			sorted = true;
		}
	}
	
}
public static void main(String[] args) throws FileNotFoundException {
	Scanner input_1 = new Scanner(System.in);
	System.out.println("Enter the name of the file");
	String in_file = input_1.nextLine();
	System.out.println("Enter the algorithm you would like to run fcfs(1), sjf(2), uni(3), rr(4)");
	int algo = input_1.nextInt();
	File file = new File("input.txt");
	Scanner input = new Scanner(file);
	int num_jobs = input.nextInt();
	ArrayList<Integer> arrival = new ArrayList<>();
	ArrayList<Integer> block = new ArrayList<>();
	ArrayList<Integer> cpu_time = new ArrayList<>();
	ArrayList<Integer> io = new ArrayList<>();
	ArrayList<Integer> ready = new ArrayList<>();
	ArrayList<Integer> running = new ArrayList<>();
	ArrayList<Integer> blocked = new ArrayList<>();
	ArrayList<Integer> jobs = new ArrayList<>();
	ArrayList<Integer> unstarted =  new ArrayList<>();
	Map<Integer,Integer> completed = new HashMap();
	
	ArrayList<Integer> decide_list = new ArrayList<>();
	Map<Integer,Integer> arrive = new HashMap();
	Map<Integer,Integer> blocks_after = new HashMap();
	Map<Integer,Integer> cpu = new HashMap();
	Map<Integer,Integer> blocks_for = new HashMap();
	Map<Integer,Integer> progress = new HashMap();
	
	
	for(int j = 1;j<num_jobs+1;j+=1) {
		jobs.add(j);
		unstarted.add(j);
		
		
	}
	for(int i = 0;i< num_jobs;i+=1) {
		arrival.add(input.nextInt());
		block.add(input.nextInt());
		cpu_time.add(input.nextInt());
		io.add(input.nextInt());
	}
	
	boolean sorted = false;
	while(sorted == false) {
		int temp_arr;
		int temp_block;
		int temp_cpu;
		int temp_io;
		for(int i = 0;i<num_jobs-1;i+=1) {
			if(arrival.get(i) > arrival.get(i+1)) {
				temp_arr = arrival.get(i);
				arrival.set(i, arrival.get(i+1));
				arrival.set(i+1, temp_arr);
				
				temp_block = block.get(i);
				block.set(i, block.get(i+1));
				block.set(i+1, temp_block);
				
				temp_cpu = cpu_time.get(i);
				cpu_time.set(i, cpu_time.get(i+1));
				cpu_time.set(i+1, temp_cpu);
				
				 temp_io = io.get(i);
				 io.set(i, io.get(i+1));
				 io.set(i+1,temp_io);
			}
		}
		int x = sort(arrival);
		if(x == 1) {
			sorted = true;
		}
	}
	Map<Integer,Integer> finish = new HashMap();
	Map<Integer,Integer> turn_around = new HashMap();
	Map<Integer,Integer> io_time = new HashMap();
	Map<Integer,Integer> waiting_time = new HashMap();
	Map<Integer,Integer> quantum = new HashMap();
	for(int i = 1; i<num_jobs+1; i +=1) {
		int a = arrival.get(i-1);
		int b = block.get(i-1);
		int c = cpu_time.get(i-1);
		int d = io.get(i-1);
		arrive.put(i, a);
		blocks_after.put(i, b);
		cpu.put(i, c);
		blocks_for.put(i, d);
		progress.put(i, 0);
		finish.put(i, 0);
		turn_around.put(i, 0);
		io_time.put(i, 0);
		waiting_time.put(i, 0);
		quantum.put(i, 2);
		
	}
	float cpu_utiz = 0;
	float io_utiz = 0;
	float throughput;
	float avg_turn;
	float avg_wait;
	
	
	
	
	
	int cycles = 0;
	Map<Integer,Integer> cpu_burst = new HashMap();
	Map<Integer,Integer> io_burst = new HashMap();
	
	if(algo == 1) {
	while(completed.size() < num_jobs) {
		if(ready.size()> 0 ) {
			for(int i = 0;i < ready.size();i+=1) {
				int job_ready = ready.get(i);
				waiting_time.put(job_ready, waiting_time.get(job_ready)+1);
			}
		}
		if(running.size()>0) {
			
			cpu_utiz +=1;
		}
		if(blocked.size()>0) {
			
			io_utiz +=1;
		}
		
		System.out.print("Before cycle    "+cycles+":    ");
		for(int j = 1;j<jobs.size()+1;j+=1) {
			if(blocked.size()>0) {
				for(int i = 0; i<blocked.size();i+=1) {
					if(blocked.get(i) == j) {
						System.out.print("blocked    "+io_burst.get(j)+"    ");
					}
				}
						
				
				
			}
			
			if(ready.size()  > 0) {
				for(int i = 0; i < ready.size();i+=1) {
					if(ready.get(i) == j) {
						System.out.print("ready    0    ");
					}
					;
				}
				
			}
			
			if(running.size() > 0) {
				if(running.get(0) == j) {
					System.out.print("running    "+cpu_burst.get(j)+"    ");
				}
				
			}
			
			if(unstarted.size()>0) {
				for(int i = 0;i<unstarted.size();i+=1) {
					
					if(unstarted.get(i) == j) {
						System.out.print("unstarted  0    ");
					}
				}
				
			}
			if(completed.size()>0) {
				for(int i = 1;i<completed.size()+1;i+=1) {
					if(completed.get(i) == j) {
						System.out.print("completed  0    ");
					}
				}
			}
		}
		System.out.print("\n");
		
		for(int i = 0; i< unstarted.size();i+=1) {
			int job = unstarted.get(i);
			if(arrive.get(job)!= null) {
				if(arrive.get(job) <= cycles) {
					ready.add(job);
					unstarted.remove(i);
				}
				
			}
			
		}
		if(running.size() > 0) {
			if(running.get(0) != null) {
				int jobo = running.get(0);
				
				if(cpu_burst.get(jobo) < 1) {
					progress.put(jobo, progress.get(jobo)+1);
					if(progress.get(jobo) >= cpu.get(jobo)) {
						
						completed.put(jobo, jobo);
						finish.put(jobo, cycles);
						turn_around.put(jobo,cycles - arrive.get(jobo));
						running.remove(0);
					}
					else {
						io_burst.put(jobo, randomOS(blocks_for.get(jobo)));
						blocked.add(jobo);
						running.remove(0);
						
					}
					
					if(ready.size() > 0) {
						running.add(ready.get(0));
						cpu_burst.put(running.get(0), randomOS(blocks_after.get(running.get(0))));
						ready.remove(0);
					}
					
				}
				else {
					
					cpu_burst.put(jobo, cpu_burst.get(jobo)-1);
					progress.put(jobo, progress.get(jobo)+1);
					if(progress.get(jobo) >= cpu.get(jobo)) {
						
						completed.put(jobo, jobo);
						finish.put(jobo, cycles);
						turn_around.put(jobo,cycles - arrive.get(jobo));
						running.remove(0);
						if(ready.size() > 0) {
							running.add(ready.get(0));
							cpu_burst.put(running.get(0), randomOS(blocks_after.get(running.get(0))));
							ready.remove(0);
						}
					}
				}
				if(blocked.size() > 0) {
					ArrayList<Integer> remove_list = new ArrayList<>();
					for(int k =0; k < blocked.size();k+=1) {
						int blockie = blocked.get(k);
						io_time.put(blockie, io_time.get(blockie)+1);
						if(io_burst.get(blockie) < 1) {
							remove_list.add(blockie);
						}
						else {
							io_burst.put(blockie, io_burst.get(blockie)-1);
						}
					}
					for(int k = 0;k<remove_list.size();k+=1) {
						int removal = remove_list.get(k);
						ready.add(removal);
						cpu_burst.put(removal, blocks_after.get(removal));
						int removal_index = 0;
						for(int j = 0;j<blocked.size();j+=1) {
							if(blocked.get(j) == removal) {
							    removal_index = j;
							}
						}
						blocked.remove(removal_index);
						
					}
					
				}
				
				
				
			}
			
		}
		
		else {
			if(ready.size()> 0) {
				
				running.add(ready.get(0));
				ready.remove(0);
				
				cpu_burst.put(running.get(0), randomOS(blocks_after.get(running.get(0))));
				
			}
			if(blocked.size()>0) {
				if(blocked.size() > 0) {
					ArrayList<Integer> remove_list = new ArrayList<>();
					for(int k =0; k < blocked.size();k+=1) {
						int blockie = blocked.get(k);
						io_time.put(blockie, io_time.get(blockie)+1);
						if(io_burst.get(blockie) < 1) {
							remove_list.add(blockie);
						}
						else {
							io_burst.put(blockie, io_burst.get(blockie)-1);
						}
					}
					for(int k = 0;k<remove_list.size();k+=1) {
						int removal = remove_list.get(k);
						ready.add(removal);
						cpu_burst.put(removal, blocks_after.get(removal));
						int removal_index = 0;
						for(int j = 0;j<blocked.size();j+=1) {
							if(blocked.get(j) == removal) {
							    removal_index = j;
							}
						}
						blocked.remove(removal_index);
						
					}
					
				}
				
			}
			
		}
		
		cycles += 1;
		
	} //end of while loop
	}//end of if statement
	if(algo == 2) {
		while(completed.size() < num_jobs) {
			if(ready.size()> 0 ) {
				for(int i = 0;i < ready.size();i+=1) {
					int job_ready = ready.get(i);
					waiting_time.put(job_ready, waiting_time.get(job_ready)+1);
				}
			}
			if(running.size()>0) {
				cpu_utiz +=1;
			}
			if(blocked.size()>0) {
				io_utiz +=1;
			}
			
			System.out.print("Before cycle    "+cycles+":    ");
			for(int j = 1;j<jobs.size()+1;j+=1) {
				if(blocked.size()>0) {
					for(int i = 0; i<blocked.size();i+=1) {
						if(blocked.get(i) == j) {
							System.out.print("blocked    "+io_burst.get(j)+"    ");
						}
					}
							
					
					
				}
				
				if(ready.size()  > 0) {
					for(int i = 0; i < ready.size();i+=1) {
						if(ready.get(i) == j) {
							System.out.print("ready    0    ");
						}
						;
					}
					
				}
				
				if(running.size() > 0) {
					if(running.get(0) == j) {
						System.out.print("running    "+cpu_burst.get(j)+"    ");
					}
					
				}
				
				if(unstarted.size()>0) {
					for(int i = 0;i<unstarted.size();i+=1) {
						
						if(unstarted.get(i) == j) {
							System.out.print("unstarted  0    ");
						}
					}
					
				}
				if(completed.size()>0) {
					for(int i = 1;i<completed.size()+1;i+=1) {
						if(completed.get(i) == j) {
							System.out.print("completed  0    ");
						}
					}
				}
			}
			System.out.print("\n");
			
			for(int i = 0; i< unstarted.size();i+=1) {
				int job = unstarted.get(i);
				if(arrive.get(job)!= null) {
					if(arrive.get(job) <= cycles) {
						ready.add(job);
						unstarted.remove(i);
					}
					
				}
				
			}
			if(running.size() > 0) {
				if(running.get(0) != null) {
					int jobo = running.get(0);
					
					if(cpu_burst.get(jobo) < 1) {
						progress.put(jobo, progress.get(jobo)+1);
						if(progress.get(jobo) >= cpu.get(jobo)) {
							
							completed.put(jobo, jobo);
							finish.put(jobo, cycles);
							turn_around.put(jobo,cycles - arrive.get(jobo));
							running.remove(0);
						}
						else {
							io_burst.put(jobo, randomOS(blocks_for.get(jobo)));
							blocked.add(jobo);
							running.remove(0);
							
						}
						if(ready.size() > 0) {
							sort_jobs(ready,progress,cpu);
							running.add(ready.get(0));
							cpu_burst.put(running.get(0), randomOS(blocks_after.get(running.get(0))));
							ready.remove(0);
						}
						
					}
					else {
						
						cpu_burst.put(jobo, cpu_burst.get(jobo)-1);
						progress.put(jobo, progress.get(jobo)+1);
						if(progress.get(jobo) >= cpu.get(jobo)) {
							
							completed.put(jobo, jobo);
							finish.put(jobo, cycles);
							turn_around.put(jobo,cycles - arrive.get(jobo));
							running.remove(0);
							if(ready.size() > 0) {
								sort_jobs(ready,progress,cpu);
								running.add(ready.get(0));
								cpu_burst.put(running.get(0), randomOS(blocks_after.get(running.get(0))));
								ready.remove(0);
							}
						}
					}
					if(blocked.size() > 0) {
						ArrayList<Integer> remove_list = new ArrayList<>();
						for(int k =0; k < blocked.size();k+=1) {
							int blockie = blocked.get(k);
							io_time.put(blockie, io_time.get(blockie)+1);
							if(io_burst.get(blockie) < 1) {
								remove_list.add(blockie);
							}
							else {
								io_burst.put(blockie, io_burst.get(blockie)-1);
							}
						}
						for(int k = 0;k<remove_list.size();k+=1) {
							int removal = remove_list.get(k);
							ready.add(removal);
							cpu_burst.put(removal, blocks_after.get(removal));
							int removal_index = 0;
							for(int j = 0;j<blocked.size();j+=1) {
								if(blocked.get(j) == removal) {
								    removal_index = j;
								}
							}
							blocked.remove(removal_index);
							
						}
						
					}
					
					
					
				}
				
			}
			
			else {
				if(ready.size()> 0) {
					sort_jobs(ready,progress,cpu);
					running.add(ready.get(0));
					ready.remove(0);
					
					cpu_burst.put(running.get(0), randomOS(blocks_after.get(running.get(0))));
					
				}
				if(blocked.size()>0) {
					if(blocked.size() > 0) {
						ArrayList<Integer> remove_list = new ArrayList<>();
						for(int k =0; k < blocked.size();k+=1) {
							int blockie = blocked.get(k);
							io_time.put(blockie, io_time.get(blockie)+1);
							if(io_burst.get(blockie) < 1) {
								remove_list.add(blockie);
							}
							else {
								io_burst.put(blockie, io_burst.get(blockie)-1);
							}
						}
						for(int k = 0;k<remove_list.size();k+=1) {
							int removal = remove_list.get(k);
							ready.add(removal);
							cpu_burst.put(removal, blocks_after.get(removal));
							int removal_index = 0;
							for(int j = 0;j<blocked.size();j+=1) {
								if(blocked.get(j) == removal) {
								    removal_index = j;
								}
							}
							blocked.remove(removal_index);
							
						}
						
					}
					
				}
				
			}
			
			cycles += 1;
			
		} //end of while loop
	} // end of if statement
	

	if(algo == 3) {
		
		while(completed.size() < num_jobs) {
			if(ready.size()> 0 ) {
				for(int i = 0;i < ready.size();i+=1) {
					int job_ready = ready.get(i);
					waiting_time.put(job_ready, waiting_time.get(job_ready)+1);
				}
			}
			if(running.size()>0) {
				cpu_utiz +=1;
			}
			if(blocked.size()>0) {
				io_utiz +=1;
			}
			System.out.print("Before cycle    "+cycles+":    ");
			for(int j = 1;j<jobs.size()+1;j+=1) {
				if(blocked.size()>0) {
					for(int i = 0; i<blocked.size();i+=1) {
						if(blocked.get(i) == j) {
							System.out.print("blocked    "+io_burst.get(j)+"    ");
						}
					}
							
					
					
				}
				
				if(ready.size()  > 0) {
					for(int i = 0; i < ready.size();i+=1) {
						if(ready.get(i) == j) {
							System.out.print("ready    0    ");
						}
						;
					}
					
				}
				
				if(running.size() > 0) {
					if(running.get(0) == j) {
						System.out.print("running    "+cpu_burst.get(j)+"    ");
					}
					
				}
				
				if(unstarted.size()>0) {
					for(int i = 0;i<unstarted.size();i+=1) {
						
						if(unstarted.get(i) == j) {
							System.out.print("unstarted  0    ");
						}
					}
					
				}
				if(completed.size()>0) {
					for(int i = 1;i<completed.size()+1;i+=1) {
						if(completed.get(i) == j) {
							System.out.print("completed  0    ");
						}
					}
				}
			}
			System.out.print("\n");
			
			for(int i = 0; i< unstarted.size();i+=1) {
				int job = unstarted.get(i);
				if(arrive.get(job)!= null) {
					if(arrive.get(job) <= cycles) {
						ready.add(job);
						unstarted.remove(i);
					}
					
				}
				
			}
			if(running.size()>0) {
				System.out.println(running.get(0));
				int jobo = running.get(0);
				
				if(cpu_burst.get(jobo) < 1) {
					progress.put(jobo, progress.get(jobo)+1);
					if(progress.get(jobo) >= cpu.get(jobo)) {
						
						completed.put(jobo, jobo);
						finish.put(jobo, cycles);
						turn_around.put(jobo,cycles - arrive.get(jobo));
						running.remove(0);
					}
					else {
						io_burst.put(jobo, randomOS(blocks_for.get(jobo)));
						blocked.add(jobo);
						running.remove(0);
						
					}
					
					
					
				}
				else {
					
					cpu_burst.put(jobo, cpu_burst.get(jobo)-1);
					progress.put(jobo, progress.get(jobo)+1);
					if(progress.get(jobo) >= cpu.get(jobo)) {
						
						completed.put(jobo, jobo);
						finish.put(jobo, cycles);
						turn_around.put(jobo,cycles - arrive.get(jobo));
						running.remove(0);
						if(ready.size() > 0) {
							
							running.add(ready.get(0));
							cpu_burst.put(running.get(0), randomOS(blocks_after.get(running.get(0))));
							ready.remove(0);
						}
					}
				}
				
			} // end of big if statement
			else {
				
				if(ready.size()>0 && blocked.size() < 1) {
					running.add(ready.get(0));
					ready.remove(0);
					
					cpu_burst.put(running.get(0), randomOS(blocks_after.get(running.get(0))));
					
				}
				else {
					if(blocked.size()>0) {
						if(blocked.size() > 0) {
							ArrayList<Integer> remove_list = new ArrayList<>();
							for(int k =0; k < blocked.size();k+=1) {
								int blockie = blocked.get(k);
								io_time.put(blockie, io_time.get(blockie)+1);
								if(io_burst.get(blockie) < 1) {
									remove_list.add(blockie);
								}
								else {
									io_burst.put(blockie, io_burst.get(blockie)-1);
								}
							}
							for(int k = 0;k<remove_list.size();k+=1) {
								int removal = remove_list.get(k);
								running.add(removal);
								cpu_burst.put(removal, blocks_after.get(removal));
								int removal_index = 0;
								for(int j = 0;j<blocked.size();j+=1) {
									if(blocked.get(j) == removal) {
									    removal_index = j;
									}
								}
								blocked.remove(removal_index);
								
							}
							
						}
						
					}
				}
			} // end of big else statement
			cycles += 1;
		}//end of while loop
	}//end of if statement
	
	if(algo == 4) {
		
		while(completed.size() < num_jobs) {
			if(ready.size()> 0 ) {
				for(int i = 0;i < ready.size();i+=1) {
					int job_ready = ready.get(i);
					waiting_time.put(job_ready, waiting_time.get(job_ready)+1);
				}
			}
			if(running.size()>0) {
				cpu_utiz +=1;
			}
			if(blocked.size()>0) {
				io_utiz +=1;
			}
			
			System.out.print("Before cycle    "+cycles+":    ");
			for(int j = 1;j<jobs.size()+1;j+=1) {
				if(blocked.size()>0) {
					for(int i = 0; i<blocked.size();i+=1) {
						if(blocked.get(i) == j) {
							System.out.print("blocked    "+io_burst.get(j)+"    ");
						}
					}
							
					
					
				}
				
				if(ready.size()  > 0) {
					for(int i = 0; i < ready.size();i+=1) {
						if(ready.get(i) == j) {
							System.out.print("ready    0    ");
						}
						;
					}
					
				}
				
				if(running.size() > 0) {
					if(running.get(0) == j) {
						System.out.print("running    "+cpu_burst.get(j)+"    ");
					}
					
				}
				
				if(unstarted.size()>0) {
					for(int i = 0;i<unstarted.size();i+=1) {
						
						if(unstarted.get(i) == j) {
							System.out.print("unstarted  0    ");
						}
					}
					
				}
				if(completed.size()>0) {
					for(int i = 1;i<completed.size()+1;i+=1) {
						if(completed.get(i) == j) {
							System.out.print("completed  0    ");
						}
					}
				}
			}
			System.out.print("\n");
			
			for(int i = 0; i< unstarted.size();i+=1) {
				int job = unstarted.get(i);
				if(arrive.get(job)!= null) {
					if(arrive.get(job) <= cycles) {
						ready.add(job);
						unstarted.remove(i);
					}
					
				}
				
			}
			if(running.size() > 0) {
				if(running.get(0) != null) {
					int jobo = running.get(0);
					
					if(cpu_burst.get(jobo) < 1 || quantum.get(jobo) < 1 ) {
						progress.put(jobo, progress.get(jobo)+1);
						if(progress.get(jobo) >= cpu.get(jobo)) {
							
							completed.put(jobo, jobo);
							finish.put(jobo, cycles);
							turn_around.put(jobo,cycles - arrive.get(jobo));
							running.remove(0);
						}
						else {
							io_burst.put(jobo, blocks_for.get(jobo));
							blocked.add(jobo);
							running.remove(0);
							
						}
						if(ready.size() > 0) {
							running.add(ready.get(0));
							if(cpu_burst.get(running.get(0)) == null) {
								cpu_burst.put(running.get(0), blocks_after.get(running.get(0)));
							}
							else {
								if(cpu_burst.get(running.get(0)) == 0) {
									cpu_burst.put(running.get(0), blocks_after.get(running.get(0)));
								}
								
							}
							
							
							ready.remove(0);
						}
						
					}
					else {
						quantum.put(jobo,quantum.get(jobo)-1);
						cpu_burst.put(jobo, cpu_burst.get(jobo)-1);
						progress.put(jobo, progress.get(jobo)+1);
						if(progress.get(jobo) >= cpu.get(jobo)) {
							
							completed.put(jobo, jobo);
							finish.put(jobo, cycles);
							turn_around.put(jobo,cycles - arrive.get(jobo));
							running.remove(0);
							if(ready.size() > 0) {
								running.add(ready.get(0));
								cpu_burst.put(running.get(0), blocks_after.get(running.get(0)));
								ready.remove(0);
							}
						}
					}
					if(blocked.size() > 0) {
						ArrayList<Integer> remove_list = new ArrayList<>();
						for(int k =0; k < blocked.size();k+=1) {
							int blockie = blocked.get(k);
							io_time.put(blockie, io_time.get(blockie)+1);
							if(io_burst.get(blockie) < 1) {
								remove_list.add(blockie);
							}
							else {
								io_burst.put(blockie, io_burst.get(blockie)-1);
							}
						}
						for(int k = 0;k<remove_list.size();k+=1) {
							int removal = remove_list.get(k);
							ready.add(removal);
							quantum.put(removal, 2);
							cpu_burst.put(removal, blocks_after.get(removal));
							int removal_index = 0;
							for(int j = 0;j<blocked.size();j+=1) {
								if(blocked.get(j) == removal) {
								    removal_index = j;
								}
							}
							blocked.remove(removal_index);
							
						}
						
					}
					
					
					
				}
				
			}
			
			else {
				if(ready.size()> 0) {
					
					running.add(ready.get(0));
					ready.remove(0);
					
					cpu_burst.put(running.get(0), blocks_after.get(running.get(0)));
					
				}
				if(blocked.size()>0) {
					if(blocked.size() > 0) {
						ArrayList<Integer> remove_list = new ArrayList<>();
						for(int k =0; k < blocked.size();k+=1) {
							int blockie = blocked.get(k);
							io_time.put(blockie, io_time.get(blockie)+1);
							if(io_burst.get(blockie) < 1) {
								remove_list.add(blockie);
							}
							else {
								io_burst.put(blockie, io_burst.get(blockie)-1);
							}
						}
						for(int k = 0;k<remove_list.size();k+=1) {
							int removal = remove_list.get(k);
							ready.add(removal);
							cpu_burst.put(removal, blocks_after.get(removal));
							int removal_index = 0;
							for(int j = 0;j<blocked.size();j+=1) {
								if(blocked.get(j) == removal) {
								    removal_index = j;
								}
							}
							blocked.remove(removal_index);
							
						}
						
					}
					
				}
				
			}
			
			cycles += 1;
			
		} //end of while loop
		}//end of if statement	
	int turn =0;
	int wait = 0;
for(int l = 0;l<num_jobs;l+=1) {
	int job = jobs.get(l);
	System.out.println("Process  "+jobs.get(l)+":");
	System.out.println("\t\t(A,B,C,IO) = ("+arrive.get(job)+","+blocks_after.get(job)+","+cpu.get(job)+","+blocks_for.get(job)+")");
	System.out.println("\t\tFinishing time: "+finish.get(job));
	System.out.println("\t\tTurnaround time: "+turn_around.get(job));
	turn += turn_around.get(job);
	wait += waiting_time.get(job);
	
	System.out.println("\t\tI/O time: "+io_time.get(job));
	System.out.println("\t\tWaiting time: "+waiting_time.get(job));
}
System.out.println("Summary Data:");
System.out.println("\t\tFinishing time: "+cycles);
float cpu_utilization = cpu_utiz/cycles;

float io_utilization = io_utiz/cycles;

System.out.println("\t\tCPU Utilization: "+cpu_utilization);
System.out.println("\t\tIO Utilization: "+io_utilization);
float cycles_mod = cycles%100;
throughput = num_jobs/cycles_mod;
System.out.println("\t\tThroughput : "+throughput+" processes per 100 cycles");
 avg_turn = turn/num_jobs;
 avg_wait = wait/num_jobs;
System.out.println("\t\tAverage turnaround time: "+avg_turn);
System.out.println("\t\tAverage waiting time: "+avg_wait);


	
	

}//end of main
}//end of class

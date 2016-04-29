# prime-numbers service

## What does is do?
 **generates/checks prime numbers** via exposed REST service.
 
## How to use it?

### Deployed public instance on EC2
 * REST API is exposed on ```http://mits4u.co.uk:8081/primes-api/index.html```. 

### Locally
 * run ```mvn clean spring-boot:run```
 * REST API will be exposed in ```http://localhost:8081/primes-api/index.html```. 

## Assumptions
 * **Caching** is out of scope. Might consider adding caching layer, auto cache warm-up, etc.
 * **Limitations** assumption is that prime numbers belong to range 0 <= n <= 2^24
  - This range will generate ~1M primes with biggest value of 16777213
  - Note: rendering all results (~10MB) on client's browser without specifying bigger ceiling in range will be very intense
 * **Very large numbers** very large numbers (i.e. > 2 ^ 24) computation involve challenges further multiplied by simultaneous requests:
  - Each request would need immense space/processing, we should consider JVM tuning, results caching and load-balancing as minimum;
  - Depending on requirements might consider Grid computing and potentially adjusting algorithm for that;
  - Might consider asynchronous-like API with separate submit/result operations to avoid blocking the client;
  - Might consider internal large number queueing approach coupled with priority based on number and results caching.
  - Might consider different form of response for the client as numbers collection as text grows rapidly for very large numbers
 * **Versioning** of API is out of scope. Would be possible incorporating in URL, using version headers, etc.
 * **NFRs** (i.e. security, auditing, monitoring, fail-overs (i.e. due to overflows), load/stress testing, etc.) is out of scope.    
 * **Statistics** collection (i.e. usage, average execution times, common ranges) is out of scope.

## Implementation details

 * We might utilize [floor, ceiling] range in each of the strategy itself. That would help saving space when result collection is very big and we will need to trim it adjusting to range anyway. Decided that extra implementation complexity is not worth the trouble. 
 * Considering big numbers, space complexity is very important here, so chose boolean[] instead of Vector<Boolean>. Also space for primes result collection would be something to watch out if to increase ceiling limitation.
 * Eratosthenes implementation uses Java 8 parallel streams backed with ForkJoinPool and auto-assigned number of threads.
 * Sundaram implementation with parallel streams showed very poor performance on > 2^20 numbers. Decided to use custom ExecutorService with CountDownLatch approach to give more flexibility to number of threads used. After some profiling it works nearly twice as fast as Eratosthenes for large numbers.  

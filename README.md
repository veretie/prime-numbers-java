# prime-numbers service

## What does is do?
 **generates/checks prime numbers** via exposed REST service.
 
## How to use it?

### Deployed public instance on EC2
 * REST API is exposed on ```http://mits4u.co.uk:8081/primes-api/index.html```. 

### Locally from IDE
 * Start uk.co.mits4u.primes.Application class
 * REST API will be exposed in ```http://localhost:8081/primes-api/index.html```. 

## Assumptions
 * **Caching** is out of scope. Might consider adding caching layer, auto cache warm-up, etc.
 * **Very large numbers**, i.e. > 2^32. Large numbers computation involve challenges further multiplied by simultaneous requests:
  @ Each request would need immense space/processing, we should consider JVM tuning, results caching and load-balancing as minimum;
  @ Depending on requirements might consider Grid computing and potentially adjusting algorithm for that;
  @ Might consider asynchronous-like API with separate submit/result operations to avoid blocking the client;
  @ Might consider internal large number queueing approach coupled with priority based on number and results caching.
 * **Versioning** of API is out of scope. Would be possible incorporating in URL, using version headers, etc.
 * **NFRs** (i.e. security, auditing, monitoring, fail-overs (i.e. due to overflows), load/stress testing, etc.) is out of scope.    
 * **Statistics** collection (i.e. usage, average execution times, common ranges) is out of scope.

## Implementation details

 * We might utilize [floor, ceiling] range in each of the strategy itself. That would help saving space when result collection is very big and we will need to trim it adjusting to range anyway. Decided that implementation complexity is not worth that. 
 * Considering big numbers, space complexity is very important here, so chose boolean[] instead of Vector<Boolean>.
 * Instead of parallel streams utilising ForkJoinPool, I have tried to use custom ExecutorService with CountDownLatch approach to give more flexibility to number of threads used. Due to worse performance decided to use streams.
 

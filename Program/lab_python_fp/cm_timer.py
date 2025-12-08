import time
from contextlib import contextmanager

# Способ 1
class cm_timer_1:
    def __enter__(self):
        self.start_time = time.perf_counter()

    def __exit__(self, exc_type, exc_val, exc_tb):
        elapsed_time = time.perf_counter() - self.start_time
        print(f"time: {elapsed_time:.4f}")
        return False

# Способ 2
@contextmanager
def cm_timer_2():
    try:
        start_time = time.perf_counter()
        yield
    finally:
        elapsed_time = time.perf_counter() - start_time
        print(f"time: {elapsed_time:.4f}")

if __name__ == "__main__":
    print("Проверка cm_timer_1:")
    with cm_timer_1():
        time.sleep(1.5)

    print("\nПроверка cm_timer_2:")
    with cm_timer_2():
        time.sleep(2.1)
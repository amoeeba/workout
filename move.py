class ParticleSimulator:
def __init__(self, particles):
self.particles = particles
def evolve(self, dt):
timestep = 0.00001
nsteps = int(dt/timestep)
for i in range(nsteps):
for p in self.particles:
# 1. calculate the direction
norm = (p.x**2 + p.y**2)**0.5
v_x = (-p.y)/norm
v_y = p.x/norm
# 2. calculate the displacement
d_x = timestep * p.ang_speed * v_x
d_y = timestep * p.ang_speed * v_y
p.x += d_x
p.y += d_y
# 3. repeat for all the time steps


from matplotlib import pyplot as plt
from matplotlib import animation
def visualize(simulator):
X = [p.x for p in simulator.particles]
Y = [p.y for p in simulator.particles]
fig = plt.figure()
ax = plt.subplot(111, aspect='equal')
line, = ax.plot(X, Y, 'ro')
# Axis limits
plt.xlim(-1, 1)
plt.ylim(-1, 1)
# It will be run when the animation starts
def init():
line.set_data([], [])
return line,
def animate(i):
# We let the particle evolve for 0.1 time units
simulator.evolve(0.01)
X = [p.x for p in simulator.particles]
Y = [p.y for p in simulator.particles]
line.set_data(X, Y)
return line,
# Call the animate function each 10 ms
anim = animation.FuncAnimation(fig, animate,
init_func=init, blit=True,# Efficient animation
interval=10)
plt.show()

def test_visualize():
particles = [Particle( 0.3, 0.5, +1),
Particle( 0.0, -0.5, -1),
Particle(-0.1, -0.4, +3)]
simulator = ParticleSimulator(particles)
visualize(simulator)
if __name__ == '__main__':
test_visualize()

def test():
particles = [Particle( 0.3, 0.5, +1),
Particle( 0.0, -0.5, -1),
Particle(-0.1, -0.4, +3)]
simulator = ParticleSimulator(particles)
simulator.evolve(0.1)
p0, p1, p2 = particles
def fequal(a, b):
return abs(a - b) < 1e-5
assert fequal(p0.x, 0.2102698450356825)
assert fequal(p0.y, 0.5438635787296997)
assert fequal(p1.x, -0.0993347660567358)
assert fequal(p1.y, -0.4900342888538049)
assert fequal(p2.x, 0.1913585038252641)
assert fequal(p2.y, -0.3652272210744360)
if __name__ == '__main__':
test()

from random import uniform
def benchmark():
particles = [Particle(uniform(-1.0, 1.0),
uniform(-1.0, 1.0),
uniform(-1.0, 1.0))
for i in range(1000)]
simulator = ParticleSimulator(particles)
simulator.evolve(0.1)
if __name__ == '__main__':
benchmark()

class Particle:
def __init__(self, x, y, ang_speed):
self.x = x
self.y = y
self.ang_speed = ang_speed
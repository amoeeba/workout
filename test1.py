

def test_visualize():
particles = [Particle( 0.3, 0.5, +1),
Particle( 0.0, -0.5, -1),
Particle(-0.1, -0.4, +3)]
simulator = ParticleSimulator(particles)
visualize(simulator)
if __name__ == '__main__':
test_visualize()

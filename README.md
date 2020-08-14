# Voronoi-image-processing

  I used voronoi cells to create cool effect images.

## How does it works

  I generate a lot of points on the image.

  For each pixel we calculate closest point and use color of this point on this coordinates.

  Ta-da!
  
  Also, I tried to implement some optimization: First algorithm was checking distance to every point doesn't matter is it close or far away, so I decided to divide image by 150x150 blocks.

## Usage

  Place your file to "input.jpg".

    $ cd voronoi-image-processing
    $ javac Main.java; java Main

  And output will be on "output.png"

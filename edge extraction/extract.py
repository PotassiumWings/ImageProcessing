import numpy as np
from utils import dot, np_value_range, normalize, show, save


def roberts(image):
    gx = np.array([[1, 0],
                   [0, -1]])
    gy = np.array([[0, 1],
                   [-1, 0]])
    hx = dot(image, gx)
    hy = dot(image, gy)
    h = np.abs(hx) + np.abs(hy)
    h = np_value_range(h)
    h = normalize(h)
    show(hx, "Roberts-hx")
    show(hy, "Roberts-hy")
    show(h, "Roberts-h")
    save("roberts-fig")
    return h, "roberts.jpg"


def prewitt(image):
    gx = np.array([[-1, -1, -1],
                   [0, 0, 0],
                   [1, 1, 1]])
    gy = np.array([[-1, 0, 1],
                   [-1, 0, 1],
                   [-1, 0, 1]])
    hx = dot(image, gx)
    hy = dot(image, gy)
    h = np.sqrt(np.power(hx, 2.0) + np.power(hy, 2.0))
    h = np_value_range(h)
    h = normalize(h)
    show(hx, "Prewitt-hx")
    show(hy, "Prewitt-hy")
    show(h, "Prewitt-h")
    save("prewitt-fig")
    return h, "prewitt.jpg"


def sobel(image):
    gx = np.array([[-1, -2, -1],
                   [0, 0, 0],
                   [1, 2, 1]])
    gy = np.array([[-1, 0, 1],
                   [-2, 0, 2],
                   [-1, 0, 1]])
    hx = dot(image, gx)
    hy = dot(image, gy)
    h = np.sqrt(np.power(hx, 2.0) + np.power(hy, 2.0))
    h = np_value_range(h)
    h = normalize(h)
    show(hx, "Sobel-hx")
    show(hy, "Sobel-hy")
    show(h, "Sobel-h")
    save("Sobel-fig")
    return h, "sobel.jpg"


def scharr(image):
    gx = np.array([[-3, -10, -3],
                   [0, 0, 0],
                   [3, 10, 3]])
    gy = np.array([[-3, 0, 3],
                   [-10, 0, 10],
                   [-3, 0, 3]])
    hx = dot(image, gx)
    hy = dot(image, gy)
    h = np.sqrt(np.power(hx, 2.0) + np.power(hy, 2.0))
    h = np_value_range(h)
    h = normalize(h)
    show(hx, "Scharr-hx")
    show(hy, "Scharr-hy")
    show(h, "Scharr-h")
    save("scharr-fig")
    return h, "scharr.jpg"


def laplace(image):
    g = np.array([[0, -1, 0],
                  [-1, 4, -1],
                  [0, -1, 0]])
    h = dot(image, g)
    h = np_value_range(h)
    h = normalize(h)
    show(h, "h")
    save("laplace-fig")
    return h, "laplace.jpg"


def laplace_8(image):
    g = np.array([[0, -1, 0],
                  [-1, 4, -1],
                  [0, -1, 0]])
    h = dot(image, g)
    h = np_value_range(h)
    h = normalize(h)
    show(h, "h")
    save("laplace8-fig")
    return h, "laplace8.jpg"


def canny(image):
    height = len(image)
    width = len(image[0])

    gauss = np.zeros((5, 5))
    for i in range(5):
        for j in range(5):
            x = i - 2
            y = j - 2
            sigma = 1
            gauss[i][j] = np.exp(-(x * x + y * y) / (2 * sigma * sigma)) / (2 * np.arccos(-1) * sigma * sigma)

    show(image, "origin")
    image = dot(image, gauss)
    show(image, "after gauss")

    gx = np.array([[-1, -2, -1],
                   [0, 0, 0],
                   [1, 2, 1]])
    gy = np.array([[-1, 0, 1],
                   [-2, 0, 2],
                   [-1, 0, 1]])
    hx = dot(image, gx)
    hy = dot(image, gy)
    h = np.sqrt(np.power(hx, 2.0) + np.power(hy, 2.0))
    h = np_value_range(h)
    h = normalize(h)
    show(h, "calc gradient")

    res = np.zeros((height, width))
    for i in range(1, height - 1):
        for j in range(1, width - 1):
            hx_ij = hx[i][j]
            hy_ij = hy[i][j]
            # up, down can be exchanged
            up = -1
            down = -1

            if hx_ij == 0:  # [90 270]
                up = h[i - 1][j]
                down = h[i + 1][j]
            elif hy_ij == 0:  # [0 180]
                up = h[i][j + 1]
                down = h[i][j - 1]
            else:
                if hy_ij <= 0:
                    hx_ij = -hx_ij
                    hy_ij = -hy_ij
                if hx_ij > hy_ij:  # [0, 45]
                    c = hy_ij / hx_ij
                    up = h[i][j + 1] * c + (1 - c) * h[i - 1][j + 1]
                    down = h[i][j - 1] * c + (1 - c) * h[i + 1][j - 1]
                elif 0 < hx_ij < hy_ij:  # [45, 90]
                    c = 1 - 1 / (hy_ij / hx_ij)
                    up = h[i - 1][j + 1] * c + (1 - c) * h[i - 1][j]
                    down = h[i + 1][j - 1] * c + (1 - c) * h[i + 1][j]
                elif 0 < -hx_ij < hy_ij:  # [90, 135]:
                    c = 1 / (-hy_ij / hx_ij)
                    up = h[i - 1][j] * c + (1 - c) * h[i - 1][j - 1]
                    down = h[i + 1][j] * c + (1 - c) * h[i + 1][j + 1]
                else:  # [135, 180]
                    c = 1 - (-hy_ij / hx_ij)
                    up = h[i - 1][j - 1] * c + (1 - c) * h[i][j - 1]
                    down = h[i + 1][j + 1] * c + (1 - c) * h[i][j + 1]

            if h[i][j] >= up and h[i][j] >= down:
                res[i][j] = h[i][j]
            else:
                res[i][j] = 0
    show(res, "filter")

    final = np.zeros((height, width))
    high_thres = 90
    low_thres = 30
    for i in range(height):
        for j in range(width):
            if res[i][j] >= high_thres:
                final[i][j] = 255
            elif res[i][j] <= low_thres:
                final[i][j] = 0
            else:
                final[i][j] = 1
    change = True
    previous = final.copy()
    show(final, "before linking")
    while change:
        change = False
        for i in range(height):
            for j in range(width):
                if previous[i][j] == 1:
                    states = [((previous[_i][_j] == 255) for _i in range(i - 1, i + 2)) for _j in range(j - 1, j + 2)]
                    if True in states:
                        final[i][j] = 255
                        change = True
                else:
                    final[i][j] = previous[i][j]
        show(final, "after linking")
    show(final, "after linking")

    save("canny-fig")
    return final, "canny.jpg"

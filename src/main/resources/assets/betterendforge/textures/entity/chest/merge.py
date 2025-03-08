from PIL import Image

def combine_images(regions):

    result = Image.new("RGBA", (128, 64))

    for cropped, dst_pos in regions:
        result.paste(cropped, dst_pos)

    return result

def preprocess(l, t, w, h):
    return (l, t, l+w, t+h)

base = "umbrella_tree_chest_"

image1 = Image.open(base+"left.png").convert("RGBA")
image2 = Image.open(base+"right.png").convert("RGBA")
double = Image.open("normal_double.png").convert("RGBA")

regions = [
    (image1.crop(preprocess(14, 0, 15, 14)), (59, 0)),
    (image1.crop(preprocess(29, 0, 15, 14)), (29, 0)),
    (image1.crop(preprocess(14, 14, 44, 5)), (29, 14)),
    (image1.crop(preprocess(14, 19, 15, 14)), (59, 19)),
    (image1.crop(preprocess(29, 19, 15, 14)), (29, 19)),
    (image1.crop(preprocess(14, 33, 44, 10)), (29, 33)),

    (image2.crop(preprocess(14, 0, 15, 14)), (44, 0)),
    (image2.crop(preprocess(29, 0, 15, 14)), (14, 0)),
    (image2.crop(preprocess(0, 14, 29, 5)), (0, 14)),
    (image2.crop(preprocess(43, 14, 15, 5)), (73, 14)),
    (image2.crop(preprocess(14, 19, 15, 14)), (44, 19)),
    (image2.crop(preprocess(29, 19, 15, 14)), (14, 19)),
    (image2.crop(preprocess(0, 33, 29, 10)), (0, 33)),
    (image2.crop(preprocess(43, 33, 15, 10)), (73, 33)),

    (double.crop((0, 0, 6, 5)), (0, 0)),
]

result = combine_images(regions)

p1 = result.crop(preprocess(14, 33, 30, 10))
p2 = result.crop(preprocess(58, 33, 30, 10)).transpose(Image.FLIP_TOP_BOTTOM)
p3 = result.crop(preprocess(14, 14, 30, 5))
p4 = result.crop(preprocess(58, 14, 30, 5)).transpose(Image.FLIP_TOP_BOTTOM)

result.paste(p1, (58, 33))
result.paste(p2, (14, 33))
result.paste(p3, (58, 14))
result.paste(p4, (14, 14))

output_path = base+"double.png"
result.save(output_path)
print(f"Image saved at {output_path}")

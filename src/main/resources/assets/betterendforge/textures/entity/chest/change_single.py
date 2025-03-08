from PIL import Image

def preprocess(l, t, w, h):
    return (l, t, l+w, t+h)

base = "umbrella_tree_chest"

result = Image.open(base+".png").convert("RGBA")


p1 = result.crop(preprocess(14, 0, 14, 14))
p2 = result.crop(preprocess(28, 0, 14, 14))
p3 = result.crop(preprocess(14, 19, 14, 14))
p4 = result.crop(preprocess(28, 19, 14, 14))

result.paste(p1, (28, 0))
result.paste(p2, (14, 0))
result.paste(p3, (28, 19))
result.paste(p4, (14, 19))

p1 = result.crop(preprocess(14, 14, 14, 5))
p2 = result.crop(preprocess(42, 14, 14, 5)).transpose(Image.FLIP_TOP_BOTTOM)
p3 = result.crop(preprocess(14, 33, 14, 10))
p4 = result.crop(preprocess(42, 33, 14, 10)).transpose(Image.FLIP_TOP_BOTTOM)

result.paste(p1, (42, 14))
result.paste(p2, (14, 14))
result.paste(p3, (42, 33))
result.paste(p4, (14, 33))

output_path = base+"_.png"
result.save(output_path)
print(f"Image saved at {output_path}")

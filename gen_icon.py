import os
from PIL import Image, ImageDraw, ImageFont

def generate_icon(size, out_path, text="Neuron"):
    # Create black background
    img = Image.new('RGB', (size, size), color='black')
    draw = ImageDraw.Draw(img)
    
    # Try to find a nice sans-serif font on macOS
    font_paths = [
        "/System/Library/Fonts/HelveticaNeue.ttc",
        "/System/Library/Fonts/Supplemental/Arial.ttf",
        "/Library/Fonts/Arial.ttf"
    ]
    font = None
    # Calculate font size: roughly 16% of image width for 'Neuron' to fit within adaptive icon safe zone (which is a 66% circle)
    font_size = int(size * 0.16)
    
    for fp in font_paths:
        if os.path.exists(fp):
            try:
                # 0 is usually the Regular face in ttc
                font = ImageFont.truetype(fp, font_size)
                break
            except:
                pass
                
    if not font:
        font = ImageFont.load_default()
        
    # Get text bounding box
    bbox = draw.textbbox((0, 0), text, font=font)
    w = bbox[2] - bbox[0]
    h = bbox[3] - bbox[1]
    
    # Center text
    x = (size - w) / 2
    y = (size - h) / 2 - (h * 0.2) # slight optical adjustment
    
    draw.text((x, y), text, fill='white', font=font)
    
    # Create dir if not exists
    os.makedirs(os.path.dirname(out_path), exist_ok=True)
    img.save(out_path)
    print(f"Generated {out_path}")

res_dir = "/Users/ansh/Documents/ANTIGRAVITY/prism/app/src/main/res"

sizes = {
    "mipmap-mdpi": 48,
    "mipmap-hdpi": 72,
    "mipmap-xhdpi": 96,
    "mipmap-xxhdpi": 144,
    "mipmap-xxxhdpi": 192
}

for folder, size in sizes.items():
    # Regular icon
    generate_icon(size, os.path.join(res_dir, folder, "ic_launcher.png"))
    # Round icon (we'll just use the same for now, or maybe add corner radius?)
    # For a purely black background, square vs round just depends on device mask,
    # but we'll save it as ic_launcher_round.png too.
    generate_icon(size, os.path.join(res_dir, folder, "ic_launcher_round.png"))

# Generate a high-res one for the foreground of adaptive icon if needed
generate_icon(432, os.path.join(res_dir, "drawable", "ic_launcher_foreground.png"))

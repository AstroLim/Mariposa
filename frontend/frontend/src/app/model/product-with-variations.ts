import { ProductVariation } from './product-variation';

export class ProductWithVariations {
    name: string = '';
    description: string = '';
    imageFile: string = '';
    variations: ProductVariation[] = [];
}

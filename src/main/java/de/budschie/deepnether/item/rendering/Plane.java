package de.budschie.deepnether.item.rendering;

import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.vector.Vector3f;

public class Plane
{
	float minAxis1, minAxis2, maxAxis1, maxAxis2, axis3;
	Axis axis1Def, axis2Def, axis3Def;
	
	protected Plane(float minAxis1, float minAxis2, float maxAxis1, float maxAxis2, float axis3, Axis axis1Def,
			Axis axis2Def, Axis axis3Def)
	{
		if(axis1Def == axis2Def || axis1Def == axis3Def || axis2Def == axis3Def)
			throw new IllegalArgumentException("There may be no duplicate axis when constructing a plane.");
		
		this.minAxis1 = Math.min(minAxis1, maxAxis1);
		this.minAxis2 = Math.min(minAxis2, maxAxis2);
		this.maxAxis1 = Math.max(minAxis1, maxAxis1);
		this.maxAxis2 = Math.max(minAxis2, maxAxis2);
		this.axis3 = axis3;
		this.axis1Def = axis1Def;
		this.axis2Def = axis2Def;
		this.axis3Def = axis3Def;
	}
	
	public Vector3f getFirstVertex()
	{
		return Plane.ofAxis(minAxis1, minAxis2, axis3, axis1Def, axis2Def, axis3Def);
	}
	
	public Vector3f getSecondVertex()
	{
		return Plane.ofAxis(minAxis1, maxAxis2, axis3, axis1Def, axis2Def, axis3Def);
	}
	
	public Vector3f getThirdVertex()
	{
		return Plane.ofAxis(maxAxis1, maxAxis2, axis3, axis1Def, axis2Def, axis3Def);
	}
	
	public Vector3f getFourthVertex()
	{
		return Plane.ofAxis(maxAxis1, minAxis2, axis3, axis1Def, axis2Def, axis3Def);
	}
	
	public float getX1()
	{
		if(axis1Def == Axis.X)
			return minAxis1;
		if(axis2Def == Axis.X)
			return minAxis2;
		return axis3;
	}
	
	public float getX2()
	{
		if(axis1Def == Axis.X)
			return maxAxis1;
		if(axis2Def == Axis.X)
			return maxAxis2;
		return axis3;
	}
	
	public float getY1()
	{
		if(axis1Def == Axis.Y)
			return minAxis1;
		if(axis2Def == Axis.Y)
			return minAxis2;
		return axis3;
	}
	
	public float getY2()
	{
		if(axis1Def == Axis.Y)
			return maxAxis1;
		if(axis2Def == Axis.Y)
			return maxAxis2;
		return axis3;
	}
	
	public float getZ1()
	{
		if(axis1Def == Axis.Z)
			return minAxis1;
		if(axis2Def == Axis.Z)
			return minAxis2;
		return axis3;
	}
	
	public float getZ2()
	{
		if(axis1Def == Axis.Z)
			return maxAxis1;
		if(axis2Def == Axis.Z)
			return maxAxis2;
		return axis3;
	}
	
	public static Vector3f ofAxis(float pos1, float pos2, float pos3, Axis a1, Axis a2, Axis a3)
	{
		float x, y, z;
		
		if(a1 == Axis.X)
			x = pos1;
		else if(a2 == Axis.X)
			x = pos2;
		else
			x = pos3;
		
		if(a1 == Axis.Y)
			y = pos1;
		else if(a2 == Axis.Y)
			y = pos2;
		else
			y = pos3;
		
		if(a1 == Axis.Z)
			z = pos1;
		else if(a2 == Axis.Z)
			z = pos2;
		else
			z = pos3;
		
		return new Vector3f(x, y, z);
	}
	
	public Vector3f getNormal()
	{
		Vector3f edge1 = getFirstVertex(), edge2 = getFirstVertex();
		
		edge1.sub(getSecondVertex());
		edge2.sub(getFourthVertex());
		
		edge1.normalize();
		edge2.normalize();
		
		edge1.cross(edge2);
		
		return edge1;
	}
	
	public static Plane getXYPlane(float minX, float maxX, float minY, float maxY, float z)
	{
		return new Plane(minX, minY, maxX, maxY, z, Axis.X, Axis.Y, Axis.Z);
	}
	
	public static Plane getXZPlane(float minX, float maxX, float y, float minZ, float maxZ)
	{
		return new Plane(minX, minZ, maxX, maxZ, y, Axis.X, Axis.Z, Axis.Y);
	}
	
	public static Plane getYZPlane(float x, float minY, float maxY, float minZ, float maxZ)
	{
		return new Plane(minY, minZ, maxY, maxZ, x, Axis.Y, Axis.Z, Axis.X);
	}
}
